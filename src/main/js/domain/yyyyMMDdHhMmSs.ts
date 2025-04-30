export class YyyyMMDdHhMmSs {

    public static valueOfYyyyMMDdHhMmSs(field: string): YyyyMMDdHhMmSs {
        if (YyyyMMDdHhMmSs.isFourteenDigitNumber(field)) {
            return new YyyyMMDdHhMmSs(field);
        }
        return new YyyyMMDdHhMmSs(null);
    }

    private static isFourteenDigitNumber(str: string) {
        return /^\d{14,}$/.test(str)
    };

    private readonly _field: string | null;

    constructor(field: string | null) {
        this._field = field;
    }

    public isEmpty(): boolean {
        return this._field == null;
    }

    public toYyyySlashMMSlashDdSpaceHhColonMm(): string {
        if (this._field == null) { return ""; }
        const yyyyMMDdHhMmSs = this._field;
        return `${yyyyMMDdHhMmSs.substring(0, 4)}/${yyyyMMDdHhMmSs.substring(4, 6)}/${yyyyMMDdHhMmSs.substring(6, 8)} ${yyyyMMDdHhMmSs.substring(8, 10)}:${yyyyMMDdHhMmSs.substring(10, 12)}`;
    }
}
