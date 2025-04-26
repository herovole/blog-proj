import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagButton} from "./tagButton";
import {TagUnits} from "./tagUnits"

type TagButtonProps = {
    tagUnitList: TagUnits;
    tagIds: readonly string[];
    searchBaseUrl: string;
    searchKey: string;
};

export const TagButtons: React.FC<TagButtonProps> = ({tagUnitList, tagIds, searchBaseUrl, searchKey}) => {


    return (
        <span>
            {tagIds.map(id => {
                return <span key="">
                    <TagButton unit={tagUnitList.getTagUnit(id)}
                               searchBaseUrl={searchBaseUrl}
                               searchKey={searchKey}
                    /></span>
            })}
        </span>
    );
};
