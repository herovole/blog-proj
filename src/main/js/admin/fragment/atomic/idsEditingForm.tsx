import React, {useState} from 'react';
import {ElementId} from "../../../domain/elementId/elementId";

type PixelValue = `${number}px`;
type IdsEditingFormProps = {
    children?: React.ReactNode;
    postKey: ElementId;
    isFixed?: boolean;
    width?: PixelValue;
    height?: PixelValue;
}

export const IdsEditingForm: React.FC<IdsEditingFormProps> = ({
                                                                  children = null,
                                                                  postKey,
                                                                  isFixed = false,
                                                                  width = "100px",
                                                                  height = "30px"
                                                              }) => {

    const SPLITTER: string = ",";
    const initIds = children ? (children as string).toString().split(SPLITTER).map((n) => Number(n)) : new Array<number>();

    const [ids, setIds] = useState<Array<number>>(initIds);
    const [fixedIds, setFixedIds] = useState<Array<number>>(initIds);
    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const currentText = e.currentTarget.value;
        setIds(
            currentText.split(SPLITTER).map(e => Number(e.trim()))
        );
    }

    const edit = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setIsBeingEdited(true);
    }

    const fix = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setFixedIds(ids);
        setIsBeingEdited(false);
    }

    const cancel = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setIds(fixedIds);
        setIsBeingEdited(false);
    }

    if (isBeingEdited && !isFixed) {
        return (
            <div>
                <input
                    className="admin-editable-text-activated"
                    style={{width, height}}
                    pattern="^[0-9,]*$"
                    placeholder="comma separated integer ids"
                    value={ids?.join(SPLITTER)}
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
            <>
                <button type="button"
                        className={isFixed ? "admin-non-editable-text" : "admin-editable-text"}
                        style={{width, height}}
                        onClick={edit}>
                    <span className="admin-editable-text-content">
                        {fixedIds && fixedIds.length > 0 ? fixedIds?.join(SPLITTER) : "(No IDs)"}
                    </span>
                </button>
                {fixedIds?.map((v: number, i: number) => (
                    <input type="hidden"
                           key={"idsEditingForm" + i.toString()}
                           name={postKey.append(i.toString()).toStringKey()}
                           value={v.toString()}/>
                ))}
            </>
        );
    }
}

