import React, {useState} from 'react';
import {ElementId} from "../../../domain/elementId/elementId";

type PixelValue = `${number}px`;
type IdsEditingFormProps = {
    children: React.ReactNode;
    postKey: ElementId;
    isFixed?: boolean;
    width?: PixelValue;
}

export const IdsEditingForm: React.FC<IdsEditingFormProps> = ({
                                                                  children,
                                                                  postKey,
                                                                  isFixed = false,
                                                                  width = "100px"
                                                              }) => {

    const SPLITTER: string = ",";
    const initIds = children?.toString().split(SPLITTER).map((n) => Number(n));

    const [ids, setIds] = useState<Array<number> | undefined>(initIds);
    const [fixedIds, setFixedIds] = useState<Array<number> | undefined>(initIds);
    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const currentText = e.currentTarget.value;
        setIds(
            currentText.split(SPLITTER).map(e => Number(e.trim()))
        );
    }

    const edit = () => {
        setIsBeingEdited(true);
    }

    const fix = () => {
        setFixedIds(ids);
        setIsBeingEdited(true);
    }

    const cancel = () => {
        setIds(fixedIds);
        setIsBeingEdited(false);
    }

    if (isBeingEdited && !isFixed) {
        return (
            <div>
                <input
                    className="admin-editable-text-activated"
                    style={{width}}
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
                        className="admin-editable-text"
                        style={{width}}
                        onClick={edit}>
                    {fixedIds && fixedIds.length > 0 ? fixedIds?.join(SPLITTER) : "(No IDs)"}
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

