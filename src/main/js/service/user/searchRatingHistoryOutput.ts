export interface SearchRatingHistoryOutputFields {
    content: { logs: { commentSerialNumber: number, rating: number }[] };
}

export class SearchRatingHistoryOutput {

    static empty(): SearchRatingHistoryOutput {
        return new SearchRatingHistoryOutput({content: {logs: []}});
    }

    private readonly fields: SearchRatingHistoryOutputFields | null;

    constructor(fields: SearchRatingHistoryOutputFields | null) {
        this.fields = fields;
    }

    findRatingByCommentSerialNumber(commentSerialNumber: number): number {
        if (!this.fields) return 0;
        const log = this.fields.content.logs.find(e => e.commentSerialNumber === commentSerialNumber);
        if (log) {
            return log.rating;
        } else {
            return 0;
        }
    }
}