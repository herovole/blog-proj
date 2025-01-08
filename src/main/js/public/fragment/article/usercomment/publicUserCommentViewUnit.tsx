import React from 'react';
import {ElementId} from "../../../../domain/elementId/elementId";
import {UserCommentUnit} from "../../../../domain/comment/userCommentUnit";

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

    const handleReport = () => {
    }
    const handleLikes = () => {
    }
    const handleDislikes = () => {
    }
    const handleOnClickReference = () => {
        handleReference(content.body.commentId);
    }

    return (
        <div className="user-comment-individual">
            <span>{content.body.commentId}:</span>
            <span className="comment-handle">{content.body.handleName}</span>
            <span>{content.body.postTimestamp}</span>
            <span>ID:{content.body.dailyUserId}</span>
            <button className="report-button" type="button">管理者へ報告</button>
            <button type="button" onClick={handleOnClickReference}>このコメントへ返信</button>
            <div className="user-comment-text">{content.body.commentText}
                <div>
                    <button type="button">+</button>
                    <span> {content.body.likes}</span></div>
                <div>
                    <button type="button">-</button>
                    <span> {content.body.dislikes}</span></div>
            </div>
        </div>
    );
}

