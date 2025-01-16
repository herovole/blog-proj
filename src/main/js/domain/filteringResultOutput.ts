export interface FilteringResultOutputFields {
    hasPassed: boolean;
    code: string;
    timestampBannedUntil: string;
    message: string;
}

export class FilteringResultOutput {

    private static readonly CODE_SERVER_ERROR: string = "SVR";
    private static readonly CODE_THREATENING_PHRASE: string = "THR";
    private static readonly CODE_BOT: string = "BOT";
    private static readonly CODE_BANNED: string = "BAN";
    private static readonly CODE_AUTH_FAILURE: string = "ATH";

    private readonly fields: FilteringResultOutputFields;

    constructor(fields: FilteringResultOutputFields) {
        this.fields = fields;
    }

    getMessage(prefix: string): string {
        if (this.fields.hasPassed) {
            return "";
        }
        console.error("server response " + this.fields.message);
        if (this.fields.code === FilteringResultOutput.CODE_SERVER_ERROR) {
            return prefix + ": システムトラブル";
        }
        if (this.fields.code === FilteringResultOutput.CODE_THREATENING_PHRASE) {
            return prefix + ": 不適切な語句を含んでいます。";
        }
        if (this.fields.code === FilteringResultOutput.CODE_BOT) {
            return prefix + ": BOTを介した操作はお控え下さい。";
        }
        if (this.fields.code === FilteringResultOutput.CODE_BANNED) {
            return prefix + ": BANされています。解除予定 - " + this.fields.timestampBannedUntil;
        }
        if (this.fields.code === FilteringResultOutput.CODE_AUTH_FAILURE) {
            return prefix + ": 有効なトークンがありません。";
        }
        return prefix + ": 分類不能なエラー";
    }

}