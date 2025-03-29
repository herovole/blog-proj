export class YyyyMMDd {

    public static valueOfYyyyMMDd(field: string): YyyyMMDd {
        if (YyyyMMDd.isEightDigitNumber(field)) {
            return new YyyyMMDd(field);
        }
        return new YyyyMMDd(null);
    }

    private static isEightDigitNumber(str: string) {
        return /^\d{8,}$/.test(str)
    };

    private readonly _field: string | null;

    constructor(field: string | null) {
        this._field = field;
    }

    public isEmpty(): boolean {
        return this._field == null;
    }

    public toYyyySlashMMSlashDd(): string {
        if (this._field == null) { return ""; }
        const yyyyMMDd = this._field;
        return `${yyyyMMDd.substring(0, 4)}/${yyyyMMDd.substring(4, 6)}/${yyyyMMDd.substring(6, 8)}`;
    }
}
