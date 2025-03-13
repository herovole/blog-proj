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
                                                                      children = new Array<string>(),
                                                                      postKey,
                                                                      candidates,
                                                                      allowsMultipleOptions = false,
                                                                      isFixed = false,
                                                                  }) => {

    let argSelectedTags: Array<string> = new Array<string>();
    if (!children) {
        argSelectedTags = new Array<string>();
    }
    if (!Array.isArray(children)) {
        argSelectedTags = new Array<string>(children as string);
    }
    if (Array.isArray(children)) {
        argSelectedTags = children.map(e => e as string);
    }

    const [selectedTags, setSelectedTags] = useState<ReadonlyArray<string>>(argSelectedTags);
    const [fixedSelectedTags, setFixedSelectedTags] = useState<ReadonlyArray<string>>(argSelectedTags);
    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false);


    const edit = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setIsBeingEdited(true);
    }

    const fix = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setFixedSelectedTags(selectedTags);
        setIsBeingEdited(false);
    }

    const cancel = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setSelectedTags(fixedSelectedTags);
        setIsBeingEdited(false);
    }

    const handleChange = (theSelectedTags:
                              ReadonlyArray<{ value: string, label: string }> | { value: string, label: string }
    ) => {

        if (Array.isArray(theSelectedTags)) {
            setSelectedTags(theSelectedTags.map(tag => tag.value));
        } else {
            const thoseSelectedTags: ReadonlyArray<string> = new Array<string>(theSelectedTags["value"]);
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

