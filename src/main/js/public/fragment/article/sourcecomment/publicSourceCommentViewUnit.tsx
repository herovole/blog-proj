import React from 'react';
import {ElementId} from "../../../../domain/elementId/elementId";
import {SourceCommentUnit} from "../../../../domain/comment/sourceCommentUnit";
import {TagUnits} from "../../../../admin/fragment/atomic/tagselectingform/tagUnits";

type PublicSourceCommentViewUnitProps = {
    postKey: ElementId;
    content: SourceCommentUnit;
    countryTagsOptions: TagUnits;
}

export const PublicSourceCommentViewUnit: React.FC<PublicSourceCommentViewUnitProps> = ({
                                                                                            postKey,
                                                                                            content,
                                                                                            countryTagsOptions
                                                                                        }) => {

    return (
        <div className="comment-section">
            <div className="source-comment-individual">
                <span>{content.body.commentId}:</span>
                <span
                    className="comment-handle">{countryTagsOptions.getJapaneseNamesByIdsForDisplay([content.body.country])}</span>
                <button type="button">Reply</button>
                <div className="source-comment-text">{content.body.commentText}</div>
            </div>
        </div>
    );
}

