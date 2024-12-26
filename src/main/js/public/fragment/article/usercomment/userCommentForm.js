import React from 'react';
import {useGoogleReCaptcha} from 'react-google-recaptcha-v3';
import axios from "axios";

export const UserCommentForm = ({postKey, articleId, functionToRerenderParent}) => {
    const [refresh, setRefresh] = React.useState(false);
    const [token, setToken] = React.useState(null);
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "user_submitting_comment";

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (!executeRecaptcha) {
            console.error('reCAPTCHA not yet available');
            return;
        }
        const recaptchaToken = await executeRecaptcha(googleReCaptchaActionLabel);
        /*
        if (!recaptchaToken) {
            console.error('verification failed');
            return;
        }
         */
        setToken(recaptchaToken);

        const formData = new FormData(event.target);
        const postData = Object.fromEntries(formData.entries());
        try {
            const response = await axios.post("/api/v1/usercomments", postData, {
                headers: {'Content-Type': 'application/json',},
            });
            const data = await response.json();
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
                <input type="hidden" name="token" value={token}/>
            </div>
            <button type="submit">投稿</button>
        </form>
    );
}

