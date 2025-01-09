import React, {RefObject} from 'react';
import {useGoogleReCaptcha} from 'react-google-recaptcha-v3';
import axios, {AxiosResponse} from "axios";
import {ElementId} from "../../../../domain/elementId/elementId";
import Modal from "react-modal";
import {PostUserCommentInput} from "./postUserCommentInput";
import {PostUserCommentOutput, PostUserCommentOutputFields} from "./postUserCommentOutput";
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

type PublicUserCommentFormProps = {
    postKey: ElementId;
    articleId: number;
    articleTitle: string;
    refText: RefObject<HTMLTextAreaElement | null>;
    reRender: () => void;
}

export const PublicUserCommentForm: React.FC<PublicUserCommentFormProps> = (
    {postKey, articleId, articleTitle, refText, reRender}: PublicUserCommentFormProps) => {
    const DEFAULT_HANDLE:string="名無し";
    const [open, setOpen] = React.useState<boolean>(false);
    const [refresh, setRefresh] = React.useState<boolean>(false);
    const [handle, setHandle] = React.useState<string>(DEFAULT_HANDLE);
    const [text, setText] = React.useState<string>("");
    const [messageOrdinary, setMessageOrdinary] = React.useState<string>("");
    const [messageWarning, setMessageWarning] = React.useState<string>("");
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "user_submitting_comment";

    const handleHandleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const currentHandle: string = e.target.value;
        setHandle(currentHandle);
    }
    const handleTextOnChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        const currentText: string = e.target.value;
        setText(currentText);
    }
    const openModal = () => {
        setOpen(true);
    }
    const closeModal = () => {
        setOpen(false);
    }
    const afterModal = () => {
    }

    const handlePost = async (event: React.FormEvent<HTMLButtonElement>): Promise<void> => {
        event.preventDefault();

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

        const input: PostUserCommentInput = new PostUserCommentInput(
            articleId,
            handle,
            text,
            recaptchaToken
        )

        try {
            const response: AxiosResponse<PostUserCommentOutputFields> = await axios.post(input.buildUrl(), input.toPayloadHash(), {
                headers: {'Content-Type': 'application/json',},
            });
            const output: PostUserCommentOutput = new PostUserCommentOutput(response.data);
            if (output.isSuccessful()) {
                setMessageOrdinary(output.getMessage());
                setMessageWarning("");
                await Zurvan.delay(2);

                setHandle(DEFAULT_HANDLE);
                setText("");
                setMessageOrdinary("");
                closeModal();
                setRefresh(r => !r);
                reRender();
            } else {
                setMessageOrdinary("");
                setMessageWarning(output.getMessage());
            }
        } catch (error) {
            console.error('Error submitting form:', error);
        }

    }

    return (
        <div>
            <div className="comment-form-exterior">
                <div className="comment-form-interior">
                    <div className="comment-form-header">記事「{articleTitle}」へ投稿</div>
                    <input type="hidden" name={postKey.append("articleId").toStringKey()} value={articleId}/>
                    <span>
                         <span className="comment-form-label"> 名前: </span>
                         <input
                             className="comment-form-handle"
                             type="text"
                             onChange={handleHandleOnChange}
                             value={handle}
                             name={postKey.append("handleName").toStringKey()}
                             placeholder={"投稿者ハンドル"}
                         />
                    </span>
                    <br/>
                    <span>
                        <span className="comment-form-label"> 本文: </span>
                        <span>
                          <textarea
                              className="comment-form-text"
                              onChange={handleTextOnChange}
                              ref={refText}
                              name={postKey.append("text").toStringKey()}
                              value={text}
                              placeholder={"投稿内容"}
                          ></textarea>
                        </span>
                    </span>
                    <br/>
                    <button className="comment-form-submit" type="button" onClick={openModal}>確認</button>
                </div>
            </div>
            <Modal
                isOpen={open}
                onAfterOpen={afterModal}
                onRequestClose={closeModal}
                style={customStyles}
                contentLabel="Example Modal"
            >
                <div className="comment-modal-exterior">
                    <div className="comment-modal-interior">
                        <div className="comment-modal-cancel">
                            <button type="button" onClick={closeModal}>キャンセル</button>
                        </div>
                        <div className="comment-modal-header">【確認】記事「{articleTitle}」へ投稿</div>
                        <p>■投稿内容</p>
                        <div className="comment-modal-body">
                            <span>名前:</span><span>{handle}</span>
                            <span>本文:</span>
                            <div>{text}</div>
                        </div>
                        <br/>
                        <div>■利用規約<br/>
                            - 本投稿内容は当ブログ上へ公開されます。<br/>
                            - コメント内容によって発生する影響は投稿者側にて責任を負ってください。<br/>
                        </div>
                        <br/>
                        <button className="comment-modal-submit" type="button"
                                onClick={handlePost}>利用規約に同意して投稿
                        </button>
                        <span className="comment-form-process">{messageOrdinary}</span>
                        <span className="comment-form-err">{messageWarning}</span>
                    </div>
                </div>
            </Modal>
        </div>
    );
}

