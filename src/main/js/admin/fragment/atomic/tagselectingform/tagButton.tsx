import React from 'react';
import {useNavigate} from 'react-router-dom';
import {TagUnit} from "./tagUnit"

type TagButtonProps = {
    unit: TagUnit;
    searchBaseUrl: string;
    searchKey: string | null;
};


export const TagButton: React.FC<TagButtonProps> = ({unit, searchBaseUrl, searchKey}) => {

    const navigate = useNavigate();
    const handleOnClick = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        if(searchKey != null) {
            const hash = {[searchKey]: unit.fields.id.toString()};
            const urlSearchParams = new URLSearchParams(Object.entries(hash));
            navigate(`${searchBaseUrl}?${urlSearchParams.toString()}`);
        }
    }

    return (
        <button key="" className="clickable-topic-tag" onClick={handleOnClick}>{unit.fields.tagJapanese}</button>
    );
};
