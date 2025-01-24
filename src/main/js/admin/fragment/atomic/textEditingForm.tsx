import React, {useState} from 'react';
import {ElementId} from "../../../domain/elementId/elementId";

type PixelValue = `${number}px`;
type TextEditingFormProps = {
    children: React.ReactNode;
    postKey: ElementId;
    isFixed?: boolean;
    width?: PixelValue;
    height?: PixelValue;
}

export const TextEditingForm: React.FC<TextEditingFormProps> = ({
                                                                    children,
                                                                    postKey,
                                                                    isFixed = false,
                                                                    width = "100px",
                                                                    height = "12px"
                                                                }) => {
    const [editedText, setEditedText] = useState<string | undefined>(children?.toString());
    const [fixedText, setFixedText] = useState<string | undefined>(children?.toString());
    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false);

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        const currentText = e.currentTarget.value;
        setEditedText(currentText);
    }

    const edit = () => {
        setIsBeingEdited(true);
    }

    const fix = () => {
        setFixedText(editedText);
        setIsBeingEdited(false);
    }

    const cancel = () => {
        setEditedText(fixedText);
        setIsBeingEdited(false);
    }


    if (isBeingEdited && isFixed) {
        return (
            <div>
                    <textarea
                        className="admin-editable-text-activated"
                        style={{width, height}}
                        onChange={handleChange}
                        placeholder="Type here..."
                    >
                        {editedText}
                    </textarea>
                <button type="button" onClick={fix}> Fix</button>
                <button type="button" onClick={cancel}> Cancel</button>
            </div>
        );
    } else {
        return (
            <>
                <button type="button"
                        className="admin-editable-text"
                        style={{width, height}}
                        onClick={edit}>
                    {fixedText ?? "(No Text)"}
                </button>
                <input type="hidden"
                       name={postKey.toStringKey()}
                       value={fixedText}/>
            </>
        );
    }
}

