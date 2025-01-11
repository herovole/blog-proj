import React from 'react';
import {PublicUserCommentViewUnit} from './publicUserCommentViewUnit'
import {ElementId} from "../../../../domain/elementId/elementId";
import {UserCommentUnit} from "../../../../domain/comment/userCommentUnit";
import {CommentUnit} from "../../../../domain/comment/commentUnit";

type PublicUserCommentViewProps = {
    postKey: ElementId;
    commentUnits: ReadonlyArray<CommentUnit>;
    handleReference: (commentIdReferred: number) => void;
}

export const PublicUserCommentView: React.FC<PublicUserCommentViewProps> = ({postKey, commentUnits, handleReference}) => {

    return (
        <div className="comment-section">
            <p className="section-title">コメント欄</p>
            {commentUnits.map((commentUnit: CommentUnit, i: number) => {
                const unit = commentUnit as UserCommentUnit;
                const depth: number = unit.depth;
                return (
                    <div key={i} className="flex-container">
                        {[...Array(depth)].map((_, j) => (
                            <span key={j} className="left-space"/>
                        ))}
                        <PublicUserCommentViewUnit
                            postKey={postKey.append(i.toString())}
                            content={unit}
                            handleReference={handleReference}
                        />
                    </div>
                );
            })}
        </div>
    );
}

