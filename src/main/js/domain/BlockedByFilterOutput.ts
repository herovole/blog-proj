export interface BlockedByFilterOutputFields {
    code: string;
    timestampBannedUntil: string;
    message: string;
}

export class BlockedByFilterOutput {

    private static readonly CODE_SERVER_ERROR: string = "SVR";
    private static readonly CODE_THREATENING_PHRASE: string = "THR";
    private static readonly CODE_BOT: string = "BOT";
    private static readonly CODE_BANNED: string = "BAN";
    private static readonly CODE_AUTH_FAILURE: string = "ATH";

    private readonly fields: BlockedByFilterOutputFields;

    constructor(fields: BlockedByFilterOutputFields) {
        this.fields = fields;
    }

    getMessage(prefix: string): string {
        console.error("server response " + this.fields.message);
        if (this.fields.code === BlockedByFilterOutput.CODE_SERVER_ERROR) {
            return prefix + ": システムトラブル";
        }
        if (this.fields.code === BlockedByFilterOutput.CODE_THREATENING_PHRASE) {
            return prefix + ": 不適切な語句を含んでいます。";
        }
        if (this.fields.code === BlockedByFilterOutput.CODE_BOT) {
            return prefix + ": BOTを介した操作はお控え下さい。";
        }
        if (this.fields.code === BlockedByFilterOutput.CODE_BANNED) {
            return prefix + ": BANされています。解除予定 - " + this.fields.timestampBannedUntil;
        }
        if (this.fields.code === BlockedByFilterOutput.CODE_AUTH_FAILURE) {
            return prefix + ": 有効なトークンがありません。";
        }
        return prefix + ": 分類不能なエラー";
    }

}