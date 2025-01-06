import React from 'react';
import {ElementId} from "../../../../domain/elementId/elementId";
import {UserCommentUnit} from "../../../../domain/comment/userCommentUnit";

type PublicUserCommentViewerUnitProps = {
    postKey: ElementId;
    content: UserCommentUnit;
}

export const PublicUserCommentViewerUnit: React.FC<PublicUserCommentViewerUnitProps> = ({postKey, content}) => {

    const handleReport = () => {
    }
    const handleLikes = () => {
    }
    const handleDislikes = () => {
    }

    return (
        <div className="frame-unit">
            <span>{content.body.commentId} : </span>
            <span>{content.body.postTimestamp} : </span>
            <button type="button" onClick={handleReport}>Report</button>
            <div>{content.body.commentText}</div>
            <p><span>likes : {content.body.likes}
                <button type="button" onClick={handleLikes}>+</button></span></p>
            <p><span>dislikes : {content.body.dislikes}
                <button type="button" onClick={handleDislikes}>+</button></span></p>
        </div>
    );
}

