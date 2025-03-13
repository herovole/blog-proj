import React from 'react';
import {ElementId} from '../../../domain/elementId/elementId'

type BooleanSelectingFormProps = {
    children: React.ReactNode;
    postKey: ElementId;
    isFixed?: boolean
}

export const BooleanSelectingForm: React.FC<BooleanSelectingFormProps> = ({
                                                                              children, postKey, isFixed = false
                                                                          }) => {
    const [check, setCheck] = React.useState<boolean>(!!children);
    const [fixedCheck, setFixedCheck] = React.useState<boolean>(!!children);
    const [isBeingEdited, setIsBeingEdited] = React.useState<boolean>(false);


    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const isChecked = e.currentTarget.checked;
        setCheck(isChecked);
    }

    const edit = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setIsBeingEdited(true);
    }

    const fix = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setFixedCheck(check);
        setIsBeingEdited(false);
    }

    const cancel = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setCheck(fixedCheck);
        setIsBeingEdited(false);
    }

    if (isBeingEdited && !isFixed) {
        return (
            <div>
                <input className="admin-editable-text-activated"
                       type="checkbox"
                       checked={check}
                       onChange={handleChange}
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
        return (
            <button
                className={isFixed ? "admin-non-editable-text" : "admin-editable-text"}
                onClick={edit}>
                {fixedCheck ? "On" : "Off"}
                <input type="hidden"
                       name={postKey.toStringKey()}
                       value={fixedCheck.toString()}/>
            </button>
        );
    }
}

