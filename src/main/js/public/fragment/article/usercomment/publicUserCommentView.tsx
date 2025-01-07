import React from 'react';
import {PublicUserCommentViewUnit} from './publicUserCommentViewUnit'
import {ElementId} from "../../../../domain/elementId/elementId";
import {CommentUnits} from "../../../../domain/comment/commentUnits";
import {UserCommentUnit} from "../../../../domain/comment/userCommentUnit";
import {CommentUnit} from "../../../../domain/comment/commentUnit";

type PublicUserCommentViewProps = {
    postKey: ElementId;
    commentUnits: CommentUnits;
}

export const PublicUserCommentView: React.FC<PublicUserCommentViewProps> = ({postKey, commentUnits}) => {

    return (
        <div>
            {commentUnits.commentUnits.map((commentUnit: CommentUnit, i: number) => {
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
                        />
                    </div>
                );
            })}
        </div>
    );
}

