import React from 'react';
import {PublicUserCommentViewerUnit} from './publicUserCommentViewerUnit'
import {ElementId} from "../../../../domain/elementId/elementId";
import {CommentUnits} from "../../../../domain/comment/commentUnits";
import {UserCommentUnit} from "../../../../domain/comment/userCommentUnit";
import {CommentUnit} from "../../../../domain/comment/commentUnit";

type PublicUserCommentViewerProps = {
    postKey: ElementId;
    commentUnits: CommentUnits;
}

export const PublicUserCommentViewer: React.FC<PublicUserCommentViewerProps> = ({postKey, commentUnits}) => {

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
                        <PublicUserCommentViewerUnit
                            postKey={postKey.append(i.toString())}
                            content={unit}
                        />
                    </div>
                );
            })}
        </div>
    );
}

