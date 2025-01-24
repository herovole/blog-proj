import React, {useState} from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {format} from 'date-fns';
import {ElementId} from "../../../domain/elementId/elementId";

type DateSelectingFormProps = {
    children: React.ReactNode;
    postKey: ElementId;
    isFixed?: boolean;
}
export const DateSelectingForm: React.FC<DateSelectingFormProps> = ({children, postKey, isFixed = false}) => {

    function reactNodeToDateStrict(node: React.ReactNode): Date | null {
        if (node == null || node === "") return null;
        if (typeof node === "string" || typeof node === "number") {
            const date = new Date(node);
            if (!isNaN(date.getTime())) {
                return date;
            }
            throw new Error("Invalid date value");
        }
        throw new Error("ReactNode is not convertible to a Date");
    }

    const [editedDate, setEditedDate] = useState<Date | null | undefined>(reactNodeToDateStrict(children));
    const [fixedDate, setFixedDate] = useState<Date | null | undefined>(reactNodeToDateStrict(children));
    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false);


    const trackDateUpdate = (date: Date | null) => {
        setEditedDate(date);
    }

    const edit = () => {
        setIsBeingEdited(true);
    }
    const fix = () => {
        setFixedDate(editedDate);
        setIsBeingEdited(false);
    }
    const cancel = () => {
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
                        className="admin-editable-text"
                        onClick={edit}>
                    {fixedDate == null ? null : format(fixedDate, 'yyyy/MM/dd')}
                </button>
                <input type="hidden"
                       name={postKey.toStringKey()}
                       value={fixedDate == null ? "" : format(fixedDate, 'yyyy/MM/dd')}/>
            </>
        );
    }
}
