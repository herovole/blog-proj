import React from 'react';
import {SourceCommentUnit} from "../../../../domain/comment/sourceCommentUnit";
import {TagUnits} from "../../../../admin/fragment/atomic/tagselectingform/tagUnits";
import {DivText} from "../../../../admin/fragment/atomic/divText";

type PublicSourceCommentViewUnitProps = {
    content: SourceCommentUnit;
    countryTagsOptions: TagUnits;
    handleReference: (commentIdReferred: number) => void;
}

export const PublicSourceCommentViewUnit: React.FC<PublicSourceCommentViewUnitProps> = ({
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
            <span className="comment-handle">
                {countryTagsOptions.getJapaneseNamesByIdsForDisplay([content.body.country])}
            </span>
            <button type="button" onClick={handleOnClickReference}>この元記事コメントへコメント</button>
            <DivText className="source-comment-text">{content.body.commentText}</DivText>
        </div>
    );
}

