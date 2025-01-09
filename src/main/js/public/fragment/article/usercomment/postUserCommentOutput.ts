export interface PostUserCommentOutputFields {
    isNotBanned: boolean,
    timestampBannedUntil: string,
    isHuman: boolean,
    hasValidContent: boolean,
    isSuccessful: boolean
}

export class PostUserCommentOutput {
    private readonly fields: PostUserCommentOutputFields;

    constructor(fields: PostUserCommentOutputFields) {
        this.fields = fields;
    }

    getMessage(): string {
        if (!this.fields.isNotBanned) {
            return "投稿失敗: BANされています。解除予定 - " + this.fields.timestampBannedUntil;
        }
        if (!this.fields.isHuman) {
            return "投稿失敗: BOTを介した操作はお控え下さい。";
        }
        if (!this.fields.hasValidContent) {
            return "投稿失敗: 不適切な語句を含んでいます。";
        }
        if (this.fields.isSuccessful) {
            return "投稿完了: コメントありがとうございます。";
        }
        return "";
    }

    isSuccessful(): boolean {
        return this.fields.isSuccessful;
    }
}
