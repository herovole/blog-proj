import React from 'react';

export const UserCommentViewerUnit = ({postKey, userCommentUnit}) => {

    const handleReport = () => {
    }
    const handleLikes = () => {
    }
    const handleDislikes = () => {
    }

    return (
        <div className="frame-unit">
            <span>{userCommentUnit.commentId} : </span>
            <span>{userCommentUnit.postTimestamp} : </span>
            <button type="button" onclick={handleReport}>Report</button>
            <div>{userCommentUnit.text}</div>
            <p><span>likes : {userCommentUnit.likes}
                <button type="button" onclick={handleLikes}>+</button></span></p>
            <p><span>dislikes : {userCommentUnit.dislikes}
                <button type="button" onclick={handleDislikes}>+</button></span></p>
        </div>
    );
}

