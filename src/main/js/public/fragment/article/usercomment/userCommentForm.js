import React from 'react';

export const UserCommentViewer = ({postKey, articleId, evokerFunc}) => {

    const handleSubmit = (event) => {
        event.preventDefault();
        evokerFunc();
        const formData = new FormData(event.target);
        const postData = Object.fromEntries(formData.entries());
        try {
            const response = await axios.post("/api/v1/articles/" + articleId + "/usercomments", postData, {
                headers: { 'Content-Type': 'application/json', },
            });
            const data = await response.json();
        } catch (error) {
            console.error('Error submitting form:', error);
        }

    }

    return (
        <form onSubmit={this.handleSubmit}>
            <div class="frame-unit">
                <hidden name={postKey.append("articleId").toStringKey()} value={articleId} />
                <textarea
                  class="editable-text-activated scale-large-flexible"
                  placeholder="Comment here..."
                  name={{postKey.append("text").toStringKey()}}
                />
            </div>
            <button type="submit">投稿</button>
        </form>
    );
}

