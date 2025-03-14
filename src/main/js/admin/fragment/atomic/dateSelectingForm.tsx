import React, {useState} from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {format} from 'date-fns';
import {ElementId} from "../../../domain/elementId/elementId";

type DateSelectingFormProps = {
    children?: React.ReactNode;
    postKey: ElementId;
    isFixed?: boolean;
}
export const DateSelectingForm: React.FC<DateSelectingFormProps> = ({children = null, postKey, isFixed = false}) => {

    function reactNodeToDateStrict(node: React.ReactNode): Date | null {
        if (node == null || node === "" || node === "-") return null;
        if (typeof node === "string") {
            if (!/^\d{8}$/.test(node)) {
                throw new Error("Invalid date format. Expected yyyyMMdd. : " + node);
            }
            const year = parseInt(node.slice(0, 4), 10);
            const month = parseInt(node.slice(4, 6), 10) - 1; // Month is 0-based in JS Date
            const day = parseInt(node.slice(6, 8), 10);

            return new Date(year, month, day);
        }
        throw new Error("ReactNode is not convertible to a Date");
    }

    const [editedDate, setEditedDate] = useState<Date | null | undefined>(reactNodeToDateStrict(children));
    const [fixedDate, setFixedDate] = useState<Date | null | undefined>(reactNodeToDateStrict(children));
    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false);


    const trackDateUpdate = (date: Date | null) => {
        setEditedDate(date);
    }

    const edit = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setIsBeingEdited(true);
    }
    const fix = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setFixedDate(editedDate);
        setIsBeingEdited(false);
    }
    const cancel = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setEditedDate(fixedDate);
        setIsBeingEdited(false);
    }

    if (isBeingEdited && !isFixed) {
        return (
            <div>
                <DatePicker
                    selected={editedDate}        // The currently selected date
                    onChange={trackDateUpdate} // Callback when date changes
                    dateFormat="yyyy/MM/dd"      // Date format (optional)
                    placeholderText="Choose a date" // Placeholder text (optional)
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
            <>
                <button type="button"
                        className={isFixed ? "admin-non-editable-text" : "admin-editable-text"}
                        onClick={edit}>
                    {fixedDate == null ? "(No Date)" : format(fixedDate, 'yyyy/MM/dd')}
                </button>
                <input type="hidden"
                       name={postKey.toStringKey()}
                       value={fixedDate == null ? "" : format(fixedDate, 'yyyy/MM/dd')}/>
            </>
        );
    }
}
