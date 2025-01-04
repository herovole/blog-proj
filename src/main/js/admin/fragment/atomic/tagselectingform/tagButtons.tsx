import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagButton} from "./tagButton";
import {TagUnitList} from "./tagUnitList"

type TagButtonProps = {
    tagUnitList: TagUnitList;
    tagIds: string[];
    searchBaseUrl: string;
};

export const TagButtons: React.FC<TagButtonProps> = ({tagUnitList, tagIds, searchBaseUrl}) => {

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
