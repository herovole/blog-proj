import { jsx as _jsx } from "react/jsx-runtime";
import 'bootstrap/dist/css/bootstrap.min.css';
import { TagButton } from "./tagButton";
export const TagButtons = ({ tagUnitList, tagIds, searchBaseUrl }) => {
    return (_jsx("span", { children: tagIds.map(id => {
            return _jsx("span", { children: _jsx(TagButton, { unit: tagUnitList.getTagUnit(id), searchBaseUrl: searchBaseUrl, searchKey: "" }) }, "");
        }) }));
};
