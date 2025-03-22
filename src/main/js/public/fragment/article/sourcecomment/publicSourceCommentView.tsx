import React from 'react';
import {PublicSourceCommentViewUnit} from './publicSourceCommentViewUnit'
import {CommentUnit} from "../../../../domain/comment/commentUnit";
import {SourceCommentUnit} from "../../../../domain/comment/sourceCommentUnit";
import {TagUnits} from "../../../../admin/fragment/atomic/tagselectingform/tagUnits";

type PublicSourceCommentViewProps = {
    commentUnits: ReadonlyArray<CommentUnit>;
    countryTagsOptions: TagUnits;
    handleReference: (commentIdReferred: number) => void;
}

export const PublicSourceCommentView: React.FC<PublicSourceCommentViewProps> = ({
                                                                                    commentUnits,
                                                                                    countryTagsOptions,
                                                                                    handleReference
                                                                                }) => {

    return (
        <div className="comment-section">
            {commentUnits.map((commentUnit: CommentUnit, i: number) => {
                const unit = commentUnit as SourceCommentUnit;
                const depth: number = unit.depth;
                return (
                    <div key={i} className="flex-container">
                        {[...Array(depth)].map((_, j) => (
                            <span key={j} className="comment-left-space"/>
                        ))}
                        <PublicSourceCommentViewUnit
                            content={unit}
                            countryTagsOptions={countryTagsOptions}
                            handleReference={handleReference}
                        />
                    </div>
                );
            })}
        </div>
    );
}

