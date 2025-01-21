import React from 'react';
import {PublicUserCommentViewUnit} from './publicUserCommentViewUnit'
import {UserCommentUnit} from "../../../../domain/comment/userCommentUnit";
import {CommentUnit} from "../../../../domain/comment/commentUnit";
import {SearchRatingHistoryOutput} from "../../../../service/user/searchRatingHistoryOutput";

type PublicUserCommentViewProps = {
    commentUnits: ReadonlyArray<CommentUnit>;
    ratingHistory: SearchRatingHistoryOutput;
    handleReference: (commentIdReferred: number) => void;
}

export const PublicUserCommentView: React.FC<PublicUserCommentViewProps> = ({
                                                                                commentUnits,
                                                                                ratingHistory,
                                                                                handleReference
                                                                            }) => {

    return (
        <div className="comment-section">
            <p className="section-title">コメント欄</p>
            {commentUnits.map((commentUnit: CommentUnit, i: number) => {
                const unit = commentUnit as UserCommentUnit;
                const depth: number = unit.depth;
                const rating: number = ratingHistory.findRatingByCommentSerialNumber(unit.body.commentSerialNumber);
                console.log("commentId " + unit.body.commentId + "/serial : " + unit.body.commentSerialNumber + "/rating : " + rating);
                return (
                    <div key={i} className="flex-container">
                        {[...Array(depth)].map((_, j) => (
                            <span key={j} className="comment-left-space"/>
                        ))}
                        <PublicUserCommentViewUnit
                            content={unit}
                            rating={rating}
                            handleReference={handleReference}
                        />
                    </div>
                );
            })}
        </div>
    );
}

