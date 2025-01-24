import React, {useState} from 'react';
import {ElementId} from "../../../domain/elementId/elementId";

type TextEditingFormProps = {
    children: React.ReactNode;
    postKey: ElementId;
    isFixed?: boolean;
    isLarge?: boolean;
}

export const TextEditingForm: React.FC<TextEditingFormProps> = ({
                                                                    children,
                                                                    postKey,
                                                                    isFixed = false,
                                                                    isLarge = false
                                                                }) => {
    const [editedText, setEditedText] = useState<string | undefined>(children?.toString());
    const [fixedText, setFixedText] = useState<string | undefined>(children?.toString());
    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false);

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        const currentText = e.currentTarget.value;
        setFixedText(currentText);
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
                        className={`editable-text-activated ${isLarge ? "scale-large-flexible" : "scale-span"}`}
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
                <button type="button" className="admin-editable-text" onClick={edit}>
                    {fixedText ?? "(No Text)"}
                </button>
                <input type="hidden"
                       name={postKey.toStringKey()}
                       value={fixedText}/>
            </>
        );
    }
}

