import React, {RefObject} from 'react';
import {ElementId} from "../../../../domain/elementId/elementId";
import {UserCommentUnit} from "../../../../domain/comment/userCommentUnit";
import Modal from "react-modal/lib";
import axios, {AxiosResponse} from "axios";
import {useGoogleReCaptcha} from 'react-google-recaptcha-v3';
import {ReportUserCommentInput} from "./reportUserCommentInput";

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

        if (!executeRecaptcha) {
            console.error('reCAPTCHA not yet available');
            return;
        }
        const recaptchaToken: string = await executeRecaptcha(googleReCaptchaActionLabel);
        if (!recaptchaToken) {
            console.error('verification failed');
            return;
        }
        const input: ReportUserCommentInput = new ReportUserCommentInput(
            content.body.commentSerialNumber,
            refReport?.current?.textContent?.toString(),
            recaptchaToken
        );
        try {
            const response: AxiosResponse<string> = await axios.post(input.buildUrl(), input.toPayloadHash(), {
                headers: {'Content-Type': 'application/json',},
            });
            const responseBody: string = response.data;
            console.log(responseBody);
            if (refReport.current != null) {
                refReport.current.value = "";
            }
            closeModal();
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
                            <span className="report-form-process"> 投稿中です。しばらくお待ちください。</span><span
                            className="comment-form-err"> 投稿失敗:サイト管理上問題のある表現を含んでいます。</span>
                        </div>
                    </div>
                </Modal>
            </div>
        </div>
    )
        ;
}

