import React from 'react';
import {ElementId} from "../../../../domain/elementId/elementId";
import {SourceCommentUnit} from "../../../../domain/comment/sourceCommentUnit";
import {TagUnits} from "../../../../admin/fragment/atomic/tagselectingform/tagUnits";

type PublicSourceCommentViewUnitProps = {
    postKey: ElementId;
    content: SourceCommentUnit;
    countryTagsOptions: TagUnits;
    handleReference: (commentIdReferred: number) => void;
}

export const PublicSourceCommentViewUnit: React.FC<PublicSourceCommentViewUnitProps> = ({
                                                                                            postKey,
                                                                                            content,
                                                                                            countryTagsOptions,
                                                                                            handleReference
                                                                                        }) => {

    const handleOnClickReference = () => {
        handleReference(content.body.commentId);
    }

    return (
        <div className="source-comment-individual">
            <span>{content.body.commentId}:</span>
            <span
                className="comment-handle">{countryTagsOptions.getJapaneseNamesByIdsForDisplay([content.body.country])}</span>
            <button type="button" onClick={handleOnClickReference}>Reply</button>
            <div className="source-comment-text">{content.body.commentText}</div>
        </div>
    );
}

