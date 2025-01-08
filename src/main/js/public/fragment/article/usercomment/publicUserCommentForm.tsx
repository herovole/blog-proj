import React, {RefObject} from 'react';
import {useGoogleReCaptcha} from 'react-google-recaptcha-v3';
import axios, {AxiosResponse} from "axios";
import {ElementId} from "../../../../domain/elementId/elementId";

type PublicUserCommentFormProps = {
    postKey: ElementId;
    articleId: number;
    articleTitle: string;
    refText: RefObject<HTMLTextAreaElement | null>;
    reRender: () => void;
}

export const PublicUserCommentForm: React.FC<PublicUserCommentFormProps> = (
    {postKey, articleId, articleTitle, refText, reRender}: PublicUserCommentFormProps) => {
    const [refresh, setRefresh] = React.useState<boolean>(false);
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "user_submitting_comment";

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>): Promise<void> => {
        event.preventDefault();

        if (!executeRecaptcha) {
            console.error('reCAPTCHA not yet available');
            return;
        }
        const recaptchaToken: string = await executeRecaptcha(googleReCaptchaActionLabel);
        if (!recaptchaToken) {
            console.error('verification failed');
            return;
        }

        const form = event.target as HTMLFormElement;
        const formData: FormData = new FormData(form);
        const postData: { [k: string]: FormDataEntryValue } = Object.fromEntries(formData.entries());
        Object.assign(postData, {"token": recaptchaToken});
        try {
            const response: AxiosResponse<string> = await axios.post("/api/v1/usercomments", postData, {
                headers: {'Content-Type': 'application/json',},
            });
            const responseBody: string = response.data;
            console.log(responseBody);
            reRender();
        } catch (error) {
            console.error('Error submitting form:', error);
        }
        setRefresh(r => !r);

    }

    return (
        <form onSubmit={handleSubmit}>

            <div className="comment-form-exterior">
                <div className="comment-form-interior">
                    <div className="comment-form-header">記事「{articleTitle}」へ投稿</div>
                    <input type="hidden" name={postKey.append("articleId").toStringKey()} value={articleId}/>
                    <span>
                         <span className="comment-form-label"> 名前: </span>
                         <input
                             className="comment-form-handle"
                             type="text"
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
                              ref={refText}
                              name={postKey.append("text").toStringKey()}
                              placeholder={"投稿内容"}
                          ></textarea>
                        </span>
                    </span>
                    <br/>
                    <button className="comment-form-submit" type="submit">投稿</button>
                    <span className="comment-form-process"> 投稿中です。しばらくお待ちください。</span>
                    <span className="comment-form-err"> 投稿失敗:サイト管理上問題のある表現を含んでいます。</span>
                </div>
            </div>
        </form>
    );
}

