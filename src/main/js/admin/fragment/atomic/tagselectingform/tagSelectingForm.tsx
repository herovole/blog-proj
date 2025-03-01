import React, {useState} from 'react';
import Select from 'react-select';
import {TagUnits} from "./tagUnits";
import {ElementId} from "../../../../domain/elementId/elementId";

type TagSelectingFormProps = {
    children?: React.ReactNode;
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
    if (!children) { children = []; }
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

    const handleChange = (theSelectedTags:
                              ReadonlyArray<{ value: string, label: string }> | { value: string, label: string }
    ) => {

        if (Array.isArray(theSelectedTags)) {
            setSelectedTags(theSelectedTags.map(tag => tag.value));
        } else {
            const thoseSelectedTags: ReadonlyArray<string> = [theSelectedTags["value"]];
            setSelectedTags(thoseSelectedTags);
        }
    };

    if (isBeingEdited && !isFixed) {
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
        const tagsInJapanese = candidates.getJapaneseNamesByIdsForDisplay(fixedSelectedTags);
        const tagsInEnglish = candidates.getEnglishNamesByIdsForDisplay(fixedSelectedTags);
        return (<>
            <button
                className={isFixed ? "admin-non-editable-text" : "admin-editable-text"}
                onClick={edit}>
                {tagsInJapanese || "(None)"}
            </button>
            <br/>
            <button
                className={isFixed ? "admin-non-editable-text" : "admin-editable-text"}
                onClick={edit}>
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

