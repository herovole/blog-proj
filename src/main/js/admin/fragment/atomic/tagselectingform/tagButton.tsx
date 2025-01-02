import React from 'react';
import {useNavigate} from 'react-router-dom';
import {TagUnit} from "./tagUnitTemp"

type TagButtonProps = {
    unit: TagUnit;
    searchBaseUrl: string;
    searchKey: string;
};


export const TagButton: React.FC<TagButtonProps> = ({unit, searchBaseUrl, searchKey}) => {

    const navigate = useNavigate();
    const handleOnClick = () => {
        console.log("tag click : " + unit.id + " " + unit.nameJp)
        const hash = {[searchKey]: unit.id};
        const urlSearchParams = new URLSearchParams(Object.entries(hash));
        navigate(`${searchBaseUrl}?${urlSearchParams.toString()}`);
    }

    return (
        <button key="" className="clickable-topic-tag" onClick={handleOnClick}>{unit.nameJp}</button>
    );
};
