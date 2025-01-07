import React from 'react';
import {PublicSourceCommentViewUnit} from './publicSourceCommentViewUnit'
import {ElementId} from "../../../../domain/elementId/elementId";
import {CommentUnit} from "../../../../domain/comment/commentUnit";
import {SourceCommentUnit} from "../../../../domain/comment/sourceCommentUnit";
import {TagUnits} from "../../../../admin/fragment/atomic/tagselectingform/tagUnits";

type PublicSourceCommentViewProps = {
    postKey: ElementId;
    commentUnits: ReadonlyArray<CommentUnit>;
    countryTagsOptions: TagUnits;
}

export const PublicSourceCommentView: React.FC<PublicSourceCommentViewProps> = ({
                                                                                    postKey,
                                                                                    commentUnits,
                                                                                    countryTagsOptions
                                                                                }) => {

    return (
        <div>
            {commentUnits.map((commentUnit: CommentUnit, i: number) => {
                const unit = commentUnit as SourceCommentUnit;
                const depth: number = unit.depth;
                return (
                    <div key={i} className="flex-container">
                        {[...Array(depth)].map((_, j) => (
                            <span key={j} className="left-space"/>
                        ))}
                        <PublicSourceCommentViewUnit
                            postKey={postKey.append(i.toString())}
                            content={unit}
                            countryTagsOptions={countryTagsOptions}
                        />
                    </div>
                );
            })}
        </div>
    );
}

