import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";

export interface SearchRatingHistoryOutputFields extends BasicApiResultFields {
    content: { logs: { commentSerialNumber: number, rating: number }[] };
}

export class SearchRatingHistoryOutput extends BasicApiResult {

    static empty(): SearchRatingHistoryOutput {
        return new SearchRatingHistoryOutput(null);
    }

    constructor(fields: SearchRatingHistoryOutputFields | null) {
        super(fields);
    }

    findRatingByCommentSerialNumber(commentSerialNumber: number): number {
        if (!this.fields) return 0;
        const fields: SearchRatingHistoryOutputFields = this.fields as SearchRatingHistoryOutputFields;
        const log = fields.content.logs.find(e => e.commentSerialNumber === commentSerialNumber);
        if (log) {
            return log.rating;
        } else {
            return 0;
        }
    }
}