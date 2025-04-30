import React, {useState} from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {format} from 'date-fns';
import {ElementId} from "../../../domain/elementId/elementId";

type DateHourMinuteSelectingFormProps = {
    children?: React.ReactNode;
    hours?: number | null;
    minutes?: number | null;
    postKey: ElementId;
    isFixed?: boolean;
}
export const DateHourMinuteSelectingForm: React.FC<DateHourMinuteSelectingFormProps> = ({
                                                                                            children = null,
                                                                                            hours = null,
                                                                                            minutes = null,
                                                                                            postKey,
                                                                                            isFixed = false
                                                                                        }) => {

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
    const [editedHours, setEditedHours] = useState<number | null | undefined>(hours);
    const [fixedHours, setFixedHours] = useState<number | null | undefined>(hours);
    const [editedMinutes, setEditedMinutes] = useState<number | null | undefined>(minutes);
    const [fixedMinutes, setFixedMinutes] = useState<number | null | undefined>(minutes);
    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false);


    const trackDateUpdate = (date: Date | null) => {
        setEditedDate(date);
    }
    const trackHoursUpdate = (e: React.ChangeEvent<HTMLInputElement>) => {
        const hours = e.currentTarget.value;
        setEditedHours(parseInt(hours));
    }
    const trackMinutesUpdate = (e: React.ChangeEvent<HTMLInputElement>) => {
        const minutes = e.currentTarget.value;
        setEditedMinutes(parseInt(minutes));
    }
    const setCurrentMinutes = () => {
        const now = new Date();
        setEditedDate(now);
        setEditedHours(now.getHours());
        setEditedMinutes(now.getMinutes());
    }

    const edit = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setIsBeingEdited(true);
    }
    const fix = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setFixedDate(editedDate);
        setFixedHours(editedHours);
        setFixedMinutes(editedMinutes);
        setIsBeingEdited(false);
    }
    const cancel = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setEditedDate(fixedDate);
        setEditedHours(fixedHours);
        setEditedMinutes(fixedMinutes)
        setIsBeingEdited(false);
    }
    const yyyyMMddhhii = () => {
        if (fixedDate == null) return "";
        const date = format(fixedDate, 'yyyyMMdd');
        const hours = fixedHours == null ? "00" : fixedHours.toString().padStart(2, "0");
        const minutes = fixedMinutes == null ? "00" : fixedMinutes.toString().padStart(2, "0");
        return date + hours + minutes;
    }
    const yyyySlashMMSlashddSpacehhColonii = () => {
        if (fixedDate == null) return "(No Data)";
        const date = format(fixedDate, 'yyyy/MM/dd');
        const hours = fixedHours == null ? "00" : fixedHours.toString().padStart(2, "0");
        const minutes = fixedMinutes == null ? "00" : fixedMinutes.toString().padStart(2, "0");
        return date + " " + hours + ":" + minutes;
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
                <input
                    className="input-items-per-page"
                    type="number"
                    max="23"
                    min="0"
                    step="1"
                    placeholder="hours"
                    onChange={trackHoursUpdate}
                    value={editedHours?.toString()}
                />
                <input
                    className="input-items-per-page"
                    type="number"
                    max="59"
                    min="0"
                    step="1"
                    placeholder="minutes"
                    onChange={trackMinutesUpdate}
                    value={editedMinutes?.toString()}
                />
                <button
                    type="button"
                    onClick={setCurrentMinutes}
                >
                    Now
                </button>
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
                    {yyyySlashMMSlashddSpacehhColonii()}
                </button>
                <input type="hidden"
                       name={postKey.toStringKey()}
                       value={yyyyMMddhhii()}/>
            </>
        );
    }
}
