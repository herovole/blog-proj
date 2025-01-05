import React from 'react';
import {useGoogleReCaptcha} from 'react-google-recaptcha-v3';
import axios, {AxiosResponse} from "axios";
import {ElementId} from "../../../../domain/elementId/elementId";

type PublicUserCommentFormProps = {
    postKey: ElementId;
    articleId: number;
    functionToRerenderParent: () => void;
}

export const PublicUserCommentForm: React.FC<PublicUserCommentFormProps> = (
    {postKey, articleId, functionToRerenderParent}: PublicUserCommentFormProps) => {
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
            functionToRerenderParent();
        } catch (error) {
            console.error('Error submitting form:', error);
        }
        setRefresh(r => !r);

    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="frame-unit">
                <input type="hidden" name={postKey.append("articleId").toStringKey()} value={articleId}/>
                <input
                    type="text"
                    className="scale-span"
                    name={postKey.append("handleName").toStringKey()}
                />
                <textarea
                    className="editable-text-activated scale-large-flexible"
                    placeholder="Comment here..."
                    name={postKey.append("text").toStringKey()}
                />
            </div>
            <button type="submit">投稿</button>
        </form>
    );
}

