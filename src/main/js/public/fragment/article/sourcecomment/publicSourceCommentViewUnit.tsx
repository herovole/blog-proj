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

    const imgTag = content.body.country == "--" ? "" :
        <img src={flagPrefix + content.body.country.toUpperCase() + ".png"} alt={content.body.country}/>;


    return (
        <div className="source-comment-individual">
            <table style={{width: "100%"}}>
                <thead>
                <tr>
                    <th className="table-header-plain">
                        <span>{content.body.commentId}: </span>
                        {imgTag}
                        <span>{countryTagsOptions.getJapaneseNamesByIdsForDisplay([content.body.country])}</span>
                    </th>
                    <th className="table-header-plain-rightmost">
                        <button type="button" onClick={handleOnClickReference}>この元記事コメントへコメント</button>
                    </th>
                </tr>
                </thead>
            </table>
            <DivText className="source-comment-text">{content.body.commentText}</DivText>
        </div>
    );
}

