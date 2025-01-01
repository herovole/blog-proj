import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagButton, TagUnit} from "./tagButton";

export interface TagUnitList {
    getTagById: (id: number) => TagUnit;
}

type TagButtonProps = {
    tagUnitList: TagUnitList;
    tagIds: number[];
    searchBaseUrl: string;
};


export const TagButtons: React.FC<TagButtonProps> = ({tagUnitList, tagIds, searchBaseUrl}) => {

    return (
        <span>
            {tagIds.map(id => {
                return <span key="">
                    <TagButton unit={tagUnitList.getTagById(id)}
                               searchBaseUrl={searchBaseUrl}
                               searchKey={""}
                    /></span>
            })}
        </span>
    );
};
