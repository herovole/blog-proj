export interface RateUserCommentOutputFields {
    isNotBanned: boolean,
    timestampBannedUntil: string,
    isHuman: boolean,
    hasValidOperation: boolean,
    isSuccessful: boolean
}

export class RateUserCommentOutput {
    private readonly fields: RateUserCommentOutputFields;

    constructor(fields: RateUserCommentOutputFields) {
        this.fields = fields;
    }

    isSuccessful(): boolean {
        return this.fields.isSuccessful;
    }
}
