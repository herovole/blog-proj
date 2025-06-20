import React, {RefObject} from 'react';
import {UserCommentUnit} from "../../../../domain/comment/userCommentUnit";
import Modal from "react-modal/lib";
import {useGoogleReCaptcha} from 'react-google-recaptcha-v3';
import {ReportUserCommentInput} from "../../../../service/user/reportUserCommentInput";
import {Zurvan} from "../../../../domain/zurvan";
import {RateUserCommentInput} from "../../../../service/user/rateUserCommentInput";
import {UserService} from "../../../../service/user/userService";
import {BasicApiResult} from "../../../../domain/basicApiResult";
import {DivText} from "../../../../admin/fragment/atomic/divText";

const customStyles = {
    content: {
        top: '50%',
        left: '50%',
        right: 'auto',
        bottom: 'auto',
        marginRight: '-50%',
        transform: 'translate(-50%, -50%)',
    },
};


type PublicUserCommentViewUnitProps = {
    content: UserCommentUnit;
    rating?: number;
    handleReference?: (commentIdReferred: number) => void;
    modeAdmin?: boolean
}

export const PublicUserCommentViewUnit: React.FC<PublicUserCommentViewUnitProps> = ({
                                                                                        content,
                                                                                        rating = 0,
                                                                                        handleReference = null,
                                                                                        modeAdmin = false
                                                                                    }) => {

    const [open, setOpen] = React.useState<boolean>(false);
    const refReport: RefObject<HTMLTextAreaElement | null> = React.useRef(null);

    const [messageOrdinary, setMessageOrdinary] = React.useState<string>("");
    const [messageWarning, setMessageWarning] = React.useState<string>("");

    const userService: UserService = new UserService();

    const BTN_CLASS_OFF: string = "user-comment-rate-off";
    const BTN_CLASS_ON: string = "user-comment-rate-on";
    const [btnGoodClass, setBtnGoodClass] = React.useState<string>(0 < rating ? BTN_CLASS_ON : BTN_CLASS_OFF);
    const [btnBadClass, setBtnBadClass] = React.useState<string>(rating < 0 ? BTN_CLASS_ON : BTN_CLASS_OFF);
    const [likes, setLikes] = React.useState<number>(content.body.likes);
    const [likesBeingProcessed, setLikesBeingProcessed] = React.useState<string>("");
    const [dislikes, setDislikes] = React.useState<number>(content.body.dislikes);
    const [dislikesBeingProcessed, setDislikesBeingProcessed] = React.useState<string>("");
    const [isInProcess, setIsInProcess] = React.useState<boolean>(false);

    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "user_submitting_report";

    const handleGood = async () => {
        if (btnBadClass == BTN_CLASS_ON || isInProcess) {
            return;
        }
        setIsInProcess(true);
        setLikesBeingProcessed("処理中...");
        handleRate(1).then(isSuccessful => {
                if (isSuccessful && btnGoodClass == BTN_CLASS_OFF) {
                    setBtnGoodClass(BTN_CLASS_ON);
                    setLikes(likes + 1);
                    setLikesBeingProcessed("");
                    setIsInProcess(false);
                } else if (isSuccessful && btnGoodClass == BTN_CLASS_ON) {
                    setBtnGoodClass(BTN_CLASS_OFF);
                    setLikes(n => n - 1);
                    setLikesBeingProcessed("");
                    setIsInProcess(false);
                }
            }
        );
    }

    const handleBad = async () => {
        if (btnGoodClass == BTN_CLASS_ON || isInProcess) {
            return;
        }
        setIsInProcess(true);
        setDislikesBeingProcessed("処理中...");
        handleRate(-1).then(isSuccessful => {
            if (isSuccessful && btnBadClass == BTN_CLASS_OFF) {
                setBtnBadClass(BTN_CLASS_ON);
                setDislikes(dislikes + 1);
                setDislikesBeingProcessed("");
                setIsInProcess(false);
            }
            if (isSuccessful && btnBadClass == BTN_CLASS_ON) {
                setBtnBadClass(BTN_CLASS_OFF);
                setDislikes(n => n - 1);
                setDislikesBeingProcessed("");
                setIsInProcess(false);
            }
        });
    }

    const handleRate = async (rating: number): Promise<boolean> => {

        if (!executeRecaptcha) {
            console.error('reCAPTCHA not yet available');
            return false;
        }
        const recaptchaToken: string = await executeRecaptcha(googleReCaptchaActionLabel);
        if (!recaptchaToken) {
            console.error('verification failed');
            return false;
        }
        const input: RateUserCommentInput = new RateUserCommentInput(
            content.body.commentSerialNumber,
            rating,
            recaptchaToken
        );
        const output: BasicApiResult = await userService.rateUserComment(input);
        try {
            return output.isSuccessful();
        } catch (error) {
            console.error('Error submitting form:', error);
            return false;
        }

    }


    const handleOnClickReference = () => {
        if (handleReference) {
            handleReference(content.body.commentId);
        }
    }

    const openModal = () => {
        setOpen(true);
    }
    const closeModal = () => {
        setOpen(false);
    }
    const afterModal = () => {
    }
    const handleReport = async () => {
        if (isInProcess) return;
        setIsInProcess(true);
        setMessageOrdinary("送信中: しばらくお待ちください。");
        setMessageWarning("");

        if (!executeRecaptcha) {
            console.error('reCAPTCHA not yet available');
            setMessageOrdinary("");
            setMessageWarning("送信失敗: Webサイト保護機能にトラブル。ページ更新後に再度お試しください。");
            setIsInProcess(false);
            return;
        }
        const recaptchaToken: string = await executeRecaptcha(googleReCaptchaActionLabel);
        if (!recaptchaToken) {
            console.error('verification failed');
            setMessageOrdinary("");
            setMessageWarning("送信失敗: Webサイト保護機能にトラブル。通信状況をご確認後にに再度お試しください。");
            setIsInProcess(false);
            return;
        }
        const input: ReportUserCommentInput = new ReportUserCommentInput(
            content.body.commentSerialNumber,
            refReport?.current?.value?.toString(),
            recaptchaToken
        );

        const output: BasicApiResult = await userService.reportUserComment(input);
        if (output.isSuccessful()) {
            setMessageOrdinary(output.getMessage("送信"));
            setMessageWarning("");
            await Zurvan.delay(2);
            if (refReport.current != null) {
                refReport.current.value = "";
                setMessageOrdinary("");
            }
            closeModal();
            setIsInProcess(false);
        } else {
            setMessageOrdinary("");
            setMessageWarning(output.getMessage("送信"));
            setIsInProcess(false);
        }
    }

    const buttons = modeAdmin ? "" : <>
        <div>
            <button type="button" className={btnGoodClass} onClick={handleGood}>Good</button>
            <span> {likes}</span> <span className="comment-form-process"> {likesBeingProcessed}</span></div>
        <div>
            <button type="button" className={btnBadClass} onClick={handleBad}>Bad</button>
            <span> {dislikes}</span> <span className="comment-form-process"> {dislikesBeingProcessed}</span></div>
    </>

    const cancelReportButton = isInProcess ? "" : <button type="button" onClick={closeModal}>キャンセル</button>;

    return (
        <div className="user-comment-individual">
            <span>{content.body.commentId}:</span>
            <span className="comment-handle">{content.body.handleName} </span>
            <span>{content.body.postTimestamp} </span>
            <span>ID:{content.body.dailyUserId} </span>
            <button className="report-button" type="button" onClick={openModal}>管理者へ報告</button>
            <button type="button" onClick={handleOnClickReference}>このコメントへ返信</button>
            <DivText className="user-comment-text">
                {content.body.commentText}
                {buttons}
            </DivText>
            <div>
                <Modal
                    isOpen={open}
                    onAfterOpen={afterModal}
                    onRequestClose={closeModal}
                    style={customStyles}
                    contentLabel="Example Modal"
                >
                    <div className="report-form-exterior">
                        <div className="report-form-interior">
                            <div className="report-form-cancel">
                                {cancelReportButton}
                            </div>
                            <div className="report-form-header">コメント{content.body.commentId}を管理者へ報告</div>
                            <span className="report-form-label">原文:</span>
                            <DivText
                                className="report-reference-text">{content.body.commentText}</DivText>
                            <span className="report-form-label">報告内容:</span>
                            <textarea className="report-form-text" ref={refReport}></textarea>
                            <br/>
                            <button className="report-form-submit" type="button" onClick={handleReport}>報告</button>
                            <span className="comment-form-process">{messageOrdinary}</span>
                            <span className="comment-form-err">{messageWarning}</span>
                        </div>
                    </div>
                </Modal>
            </div>
        </div>
    )
        ;
}

