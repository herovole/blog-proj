import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagButton} from "./tagButton";
import {TagUnits} from "./tagUnits"

type TagButtonProps = {
    tagUnitList: TagUnits;
    tagIds: readonly string[];
    searchBaseUrl: string;
};

export const TagButtons: React.FC<TagButtonProps> = ({tagUnitList, tagIds, searchBaseUrl}) => {

    useEffect(() => {
        console.log("TAGBUTTONS " + JSON.stringify(tagUnitList));
        console.log("TAGBUTTONS " + JSON.stringify(tagIds));
    }, []);

    return (
        <span>
            {tagIds.map(id => {
                return <span key="">
                    <TagButton unit={tagUnitList.getTagUnit(id)}
                               searchBaseUrl={searchBaseUrl}
                               searchKey={""}
                    /></span>
            })}
        </span>
    );
};
