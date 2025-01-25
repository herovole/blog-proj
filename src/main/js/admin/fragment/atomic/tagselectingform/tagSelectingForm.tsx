import React, {useState} from 'react';
import Select from 'react-select';
import {TagUnits} from "./tagUnits";
import {ElementId} from "../../../../domain/elementId/elementId";

type TagSelectingFormProps = {
    children: React.ReactNode;
    postKey: ElementId;
    candidates: TagUnits;
    allowsMultipleOptions?: boolean;
    isFixed?: boolean;
}

export const TagSelectingForm: React.FC<TagSelectingFormProps> = ({
                                                                      children = [],
                                                                      postKey,
                                                                      candidates,
                                                                      allowsMultipleOptions = false,
                                                                      isFixed = false,
                                                                  }) => {

    const [selectedTags, setSelectedTags] = useState<ReadonlyArray<string>>(
        Array.isArray(children) ? children : [children]);
    const [fixedSelectedTags, setFixedSelectedTags] = useState<ReadonlyArray<string>>(
        Array.isArray(children) ? children : [children]);
    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false);


    const edit = () => {
        setIsBeingEdited(true);
    }

    const fix = () => {
        setFixedSelectedTags(selectedTags);
        setIsBeingEdited(false);
    }

    const cancel = () => {
        setSelectedTags(fixedSelectedTags);
        setIsBeingEdited(false);
    }

    const handleChange = (theSelectedTags: ReadonlyArray<string> | string) => {
        console.log("selected handleChange : " + JSON.stringify(theSelectedTags));

        if (Array.isArray(theSelectedTags)) {
            setSelectedTags(theSelectedTags);
        }
        if (typeof theSelectedTags === "string") {
            const thoseSelectedTags: ReadonlyArray<string> = [theSelectedTags];
            setSelectedTags(thoseSelectedTags);
        }
    };

    if (isBeingEdited && !isFixed) {
        console.log("render-active :" + JSON.stringify(candidates.getTagOptionsJapanese()));
        console.log("selected :" + JSON.stringify(selectedTags));
        return (
            <div>
                <Select
                    isMulti={allowsMultipleOptions}
                    options={candidates.getTagOptionsJapanese()}
                    value={candidates.getTagOptionsJapaneseSelected(selectedTags)}
                    onChange={handleChange}
                    placeholder="Select or type to add tags"
                />
                <Select
                    isMulti={allowsMultipleOptions}
                    options={candidates.getTagOptionsEnglish()}
                    value={candidates.getTagOptionsEnglishSelected(selectedTags)}
                    onChange={handleChange}
                    placeholder="Select or type to add tags"
                />
                <button
                    type="button"
                    onClick={fix}
                >
                    Fix
                </button>
                <button
                    type="button"
                    onClick={cancel}
                >
                    Cancel
                </button>
            </div>
        );
    } else {
        console.log("render-passive:" + JSON.stringify(candidates.getTagOptionsJapanese()));
        const tagsInJapanese = candidates.getJapaneseNamesByIdsForDisplay(fixedSelectedTags);
        const tagsInEnglish = candidates.getEnglishNamesByIdsForDisplay(fixedSelectedTags);
        return (<>
            <button className="admin-editable-text" onClick={edit}>
                {tagsInJapanese || "(None)"}
            </button>
            <button className="admin-editable-text" onClick={edit}>
                {tagsInEnglish || "(None)"}
            </button>
            {fixedSelectedTags.map((v, i) => (
                <input type="hidden"
                       key={v}
                       name={postKey.append(i.toString()).toStringKey()}
                       value={v}/>
            ))}
        </>);
    }
}

