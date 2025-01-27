export class GenericSwitch {

    static valueOfString(field: string): GenericSwitch {
        if (field === "true" || field === "1") {
            return new GenericSwitch(true);
        } else {
            return new GenericSwitch(false);
        }
    }

    private readonly _field: boolean;

    constructor(field: boolean) {
        this._field = field;
    }

    isTrue(): boolean {
        return this._field;
    }

}