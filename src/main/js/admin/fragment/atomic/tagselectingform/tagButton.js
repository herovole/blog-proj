import { jsx as _jsx } from "react/jsx-runtime";
import { useNavigate } from 'react-router-dom';
export const TagButton = ({ unit, searchBaseUrl, searchKey }) => {
    const navigate = useNavigate();
    const handleOnClick = () => {
        console.log("tag click : " + unit.id + " " + unit.nameJp);
        const hash = { [searchKey]: unit.id.toString() };
        const urlSearchParams = new URLSearchParams(Object.entries(hash));
        navigate(`${searchBaseUrl}?${urlSearchParams.toString()}`);
    };
    return (_jsx("button", { className: "clickable-topic-tag", onClick: handleOnClick, children: unit.nameJp }, ""));
};
