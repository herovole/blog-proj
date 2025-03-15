export interface BasicApiResultFields {
    code: string;
    timestampBannedUntil: string;
    message: string;
}

export class BasicApiResult {

    static empty(): BasicApiResult {
        return new BasicApiResult(null);
    }

    private static readonly CODE_NO_ERROR: string = "NNE";
    private static readonly CODE_SERVER_ERROR: string = "SVR";
    private static readonly CODE_GENERIC_USER_ERROR: string = "GUE";
    private static readonly CODE_HUMAN_THREATENING_PHRASE: string = "HTH";
    private static readonly CODE_SYSTEM_THREATENING_PHRASE: string = "STH";
    private static readonly CODE_BOT: string = "BOT";
    private static readonly CODE_BANNED: string = "BAN";
    private static readonly CODE_FREQUENT_POSTS: string = "FRP";
    private static readonly CODE_AUTH_FAILURE: string = "ATH";
    private static readonly CODE_AUTH_INSUFFICIENT: string = "AIS";

    protected readonly fields: BasicApiResultFields | null;

    constructor(fields: BasicApiResultFields | null) {
        this.fields = fields;
    }

    isSuccessful(): boolean {
        if (!this.fields) throw new Error("no data");
        return this.fields.code === BasicApiResult.CODE_NO_ERROR;
    }

    getMessage(prefix: string): string {
        if (!this.fields) throw new Error("no data");
        switch (this.fields.code) {
            case BasicApiResult.CODE_NO_ERROR:
                console.info("server response " + this.fields.message);
                return prefix + "成功";
            case BasicApiResult.CODE_SERVER_ERROR:
                console.error("server response " + this.fields.message);
                return prefix + ": システムトラブル";
            case BasicApiResult.CODE_GENERIC_USER_ERROR:
                console.error("server response " + this.fields.message);
                return prefix + ": 入力内容に不備があります。";
            case BasicApiResult.CODE_HUMAN_THREATENING_PHRASE:
                console.error("server response " + this.fields.message);
                return prefix + ": 不適切な語句を含んでいます。";
            case BasicApiResult.CODE_SYSTEM_THREATENING_PHRASE:
                console.error("server response " + this.fields.message);
                return prefix + ": 不適切な語句を含んでいます.";
            case BasicApiResult.CODE_BOT:
                console.error("server response " + this.fields.message);
                return prefix + ": BOTを介した操作はお控え下さい。";
            case BasicApiResult.CODE_BANNED:
                console.error("server response " + this.fields.message);
                return prefix + ": BANされています。解除予定 - " + this.fields.timestampBannedUntil;
            case BasicApiResult.CODE_FREQUENT_POSTS:
                console.error("server response " + this.fields.message);
                return prefix + ": 前回の投稿から間をあけてご投稿ください。 ";
            case BasicApiResult.CODE_AUTH_FAILURE:
                console.error("server response " + this.fields.message);
                return prefix + ": 有効な認証トークンがありません。";
            case BasicApiResult.CODE_AUTH_INSUFFICIENT:
                console.error("server response " + this.fields.message);
                return prefix + ": 権限不足です。";
            default:
                console.error("server response " + this.fields.message);
                return prefix + ": 分類不能なエラー";
        }
    }

}