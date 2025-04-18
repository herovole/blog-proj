import React, {useEffect, useState} from 'react';
import {SourceCommentUnit} from "../../../../domain/comment/sourceCommentUnit";
import {TagUnits} from "../../../../admin/fragment/atomic/tagselectingform/tagUnits";
import {DivText} from "../../../../admin/fragment/atomic/divText";
import {ResourceManagement} from "../../../../service/resourceManagement";

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

    const [flagPrefix, setFlagPrefix] = useState<string | null>(null);

    useEffect(() => {
        ResourceManagement.getInstance().systemImagePrefixWithSlash().then(e => setFlagPrefix(e + "flags/"));
    }, []);

    const handleOnClickReference = () => {
        handleReference(content.body.commentId);
    }

    return (
        <div className="source-comment-individual">
            <table>
                <thead>
                <tr>
                    <th className="table-header-plain">{content.body.commentId}:</th>
                    <th className="table-header-plain">
                        <img src={flagPrefix + content.body.country.toUpperCase() + ".png"} alt={content.body.country}/>
                    </th>
                    <th className="comment-handle table-header-plain">
                        {countryTagsOptions.getJapaneseNamesByIdsForDisplay([content.body.country])}
                    </th>
                    <th className="table-header-plain-align-right">
                        <button type="button" onClick={handleOnClickReference}>この元記事コメントへコメント</button>
                    </th>
                </tr>
                </thead>
            </table>
            <DivText className="source-comment-text">{content.body.commentText}</DivText>
        </div>
    );
}

