import React, {useState} from 'react';
import {ElementId} from "../../../domain/elementId/elementId";
import {DivText} from "./divText";

type PixelValue = `${number}px`;
type TextEditingFormProps = {
    children?: React.ReactNode;
    postKey: ElementId;
    isFixed?: boolean;
    width?: PixelValue;
    height?: PixelValue;
    isPassword?: boolean;
}

export const TextEditingForm: React.FC<TextEditingFormProps> = ({
                                                                    children = "",
                                                                    postKey,
                                                                    isFixed = false,
                                                                    width = "400px",
                                                                    height = "30px",
                                                                    isPassword = false,
                                                                }) => {
    const [editedText, setEditedText] = useState<string | undefined>(children?.toString());
    const [fixedText, setFixedText] = useState<string | undefined>(children?.toString());
    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false);

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        const currentText = e.currentTarget.value;
        setEditedText(currentText);
    }
    const handleChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
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

    let textToShow = "(No Text)";
    if (fixedText && !isPassword) {
        textToShow = fixedText;
    }
    if (fixedText && isPassword) {
        textToShow = "(Password Declared)";
    }


    if (isBeingEdited && !isFixed && !isPassword) {
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
    } else if (isBeingEdited && !isFixed && isPassword) {
        return <div>
            <input
                type="password"
                className="admin-editable-text-activated"
                style={{width, height}}
                onChange={handleChangePassword}
                value={editedText}
            />
            <button type="button" onClick={fix}> Fix</button>
            <button type="button" onClick={cancel}> Cancel</button>
        </div>
    } else {
        return (
            <>
                <button type="button"
                        className={isFixed ? "admin-non-editable-text" : "admin-editable-text"}
                        style={{width, height}}
                        onClick={edit}>
                    <DivText className="admin-editable-text-content">
                        {textToShow}
                    </DivText>
                </button>
                <input type="hidden"
                       name={postKey.toStringKey()}
                       value={fixedText}/>
            </>
        );
    }
}

