import React from 'react';

export const PublicUserCommentViewerUnit = ({postKey, content}) => {

    const handleReport = () => {
    }
    const handleLikes = () => {
    }
    const handleDislikes = () => {
    }

    return (
        <div className="frame-unit">
            <span>{content.commentId} : </span>
            <span>{content.postTimestamp} : </span>
            <button type="button" onclick={handleReport}>Report</button>
            <div>{content.text}</div>
            <p><span>likes : {content.likes}
                <button type="button" onclick={handleLikes}>+</button></span></p>
            <p><span>dislikes : {content.dislikes}
                <button type="button" onclick={handleDislikes}>+</button></span></p>
        </div>
    );
}

