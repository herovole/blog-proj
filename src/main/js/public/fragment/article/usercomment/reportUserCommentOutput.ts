export interface ReportUserCommentOutputFields {
    isNotBanned: boolean,
    timestampBannedUntil: string,
    isHuman: boolean,
    hasValidContent: boolean,
    isSuccessful: boolean
}

export class ReportUserCommentOutput {
    private readonly fields: ReportUserCommentOutputFields;

    constructor(fields: ReportUserCommentOutputFields) {
        this.fields = fields;
    }

    getMessage(): string {
        if (!this.fields.isNotBanned) {
            return "送信失敗: BANされています。解除予定 - " + this.fields.timestampBannedUntil;
        }
        if (!this.fields.isHuman) {
            return "送信失敗: BOTを介した操作はお控え下さい。";
        }
        if (!this.fields.hasValidContent) {
            return "送信失敗: 不適切な語句を含んでいます。";
        }
        if (this.fields.isSuccessful) {
            return "送信成功: ご連絡ありがとうございます。";
        }
        return "";
    }

    isSuccessful(): boolean {
        return this.fields.isSuccessful;
    }
}
