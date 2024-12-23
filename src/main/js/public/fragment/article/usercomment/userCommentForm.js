import React from 'react';
import {GoogleReCaptcha} from 'react-google-recaptcha-v3';

export const UserCommentForm = ({postKey, articleId, evokerFunc}) => {
    const [refresh, setRefresh] = React.useState(false);
    const [token, setToken] = React.useState(null);

    const handleSubmit = (event) => {
        event.preventDefault();
        evokerFunc();
        const formData = new FormData(event.target);
        const postData = Object.fromEntries(formData.entries());
        try {
            //const response = await axios.post("/api/v1/articles/" + articleId + "/usercomments", postData, {
            //    headers: {'Content-Type': 'application/json',},
            //});
            //const data = await response.json();
        } catch (error) {
            console.error('Error submitting form:', error);
        }

    }

    const onVerify = (token) => {
        setToken(token);
    }
    const refreshReCaptcha = () => {
        setRefresh(r => !r);
    }

    return (
        <>
            <form onSubmit={this.handleSubmit}>
                <div className="frame-unit">
                    <hidden name={postKey.append("articleId").toStringKey()} value={articleId}/>
                    <textarea
                        className="editable-text-activated scale-large-flexible"
                        placeholder="Comment here..."
                        name={postKey.append("text").toStringKey()}
                    />
                    <input type="hidden" name="token" value={token}/>
                </div>
                <button type="submit">投稿</button>
            </form>
            <GoogleReCaptcha
                onVerify={onVerify}
                refreshReCaptcha={refreshReCaptcha}
            />
        </>
    );
}

