import {Person} from "../admin/person";


export class GenericSwitch {
    private _field: boolean;

    constructor(field: string) {
        const p:Person = new Person("f", "l", 99);
        console.log(p.getFullName());
        if (field === "true" || field === "1") {
            this._field = true;
        } else {
            this._field = false;
        }
    }

    isTrue(): boolean {
        return this._field;
    }
}