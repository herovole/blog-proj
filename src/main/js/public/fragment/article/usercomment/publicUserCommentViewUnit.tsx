import React, {RefObject} from 'react';
import {ElementId} from "../../../../domain/elementId/elementId";
import {UserCommentUnit} from "../../../../domain/comment/userCommentUnit";
import Modal from "react-modal/lib";
import axios from "axios";
import {useGoogleReCaptcha} from 'react-google-recaptcha-v3';
import {ReportUserCommentInput} from "./reportUserCommentInput";
import {ReportUserCommentOutput, ReportUserCommentOutputFields} from "./reportUserCommentOutput";
import {Zurvan} from "../../../../domain/zurvan";

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
    postKey: ElementId;
    content: UserCommentUnit;
    handleReference: (commentIdReferred: number) => void;
}

export const PublicUserCommentViewUnit: React.FC<PublicUserCommentViewUnitProps> = ({
                                                                                        postKey,
                                                                                        content,
                                                                                        handleReference
                                                                                    }) => {

    const [open, setOpen] = React.useState<boolean>(false);
    const refReport: RefObject<HTMLTextAreaElement | null> = React.useRef(null);
    const [messageOrdinary, setMessageOrdinary] = React.useState<string>("");
    const [messageWarning, setMessageWarning] = React.useState<string>("");
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "user_submitting_report";

    const handleLikes = () => {
    }
    const handleDislikes = () => {
    }
    const handleOnClickReference = () => {
        handleReference(content.body.commentId);
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
        setMessageOrdinary("送信中: しばらくお待ちください。");
        setMessageWarning("");

        if (!executeRecaptcha) {
            console.error('reCAPTCHA not yet available');
            setMessageOrdinary("");
            setMessageWarning("送信失敗: Webサイト保護機能にトラブル。ページ更新後に再度お試しください。");
            return;
        }
        const recaptchaToken: string = await executeRecaptcha(googleReCaptchaActionLabel);
        if (!recaptchaToken) {
            console.error('verification failed');
            setMessageOrdinary("");
            setMessageWarning("送信失敗: Webサイト保護機能にトラブル。通信状況をご確認後にに再度お試しください。");
            return;
        }
        const input: ReportUserCommentInput = new ReportUserCommentInput(
            content.body.commentSerialNumber,
            refReport?.current?.value?.toString(),
            recaptchaToken
        );
        try {
            const response = await axios.post(input.buildUrl(), input.toPayloadHash(), {
                headers: {'Content-Type': 'application/json',},
            });
            const output: ReportUserCommentOutput = new ReportUserCommentOutput(response.data as ReportUserCommentOutputFields);
            if (output.isSuccessful()) {
                setMessageOrdinary(output.getMessage());
                setMessageWarning("");
                await Zurvan.delay(2);
                if (refReport.current != null) {
                    refReport.current.value = "";
                    setMessageOrdinary("");
                }
                closeModal();
            } else {
                setMessageOrdinary("");
                setMessageWarning(output.getMessage());
            }
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    }

    return (
        <div className="user-comment-individual">
            <span>{content.body.commentId}:</span>
            <span className="comment-handle">{content.body.handleName}</span>
            <span>{content.body.postTimestamp}</span>
            <span>ID:{content.body.dailyUserId}</span>
            <button className="report-button" type="button" onClick={openModal}>管理者へ報告</button>
            <button type="button" onClick={handleOnClickReference}>このコメントへ返信</button>
            <div className="user-comment-text">{content.body.commentText}
                <div>
                    <button type="button">+</button>
                    <span> {content.body.likes}</span></div>
                <div>
                    <button type="button">-</button>
                    <span> {content.body.dislikes}</span></div>
            </div>
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
                                <button type="button" onClick={closeModal}>キャンセル</button>
                            </div>
                            <div className="report-form-header">コメント{content.body.commentId}を管理者へ報告</div>
                            <span className="report-form-label">原文:</span>
                            <div className="report-reference-text">{content.body.commentText}</div>
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

