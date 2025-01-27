import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";
import {UserCommentUnit} from "../../domain/comment/userCommentUnit";

export interface SearchCommentsOutputFields extends BasicApiResultFields {
    content: {
        comments: ReadonlyArray<CommentAndReport>;
        total: number;
    };
}

export interface ReportUnit {
    id: number,
    userId: number,
    userBannedUntil: string;
    hasUserBanned: boolean;

    ip: string,
    ipBannedUntil: string;
    hasIpBanned: boolean;

    text: string,
    timestampFiled: string,
    isHandled: boolean,
}

export interface CommentAndReport {
    comment: UserCommentUnit,
    article: {
        id: number,
        title: string
    },
    reports: ReadonlyArray<ReportUnit>
}

export class SearchCommentsOutput extends BasicApiResult {

    static empty(): SearchCommentsOutput {
        return new SearchCommentsOutput(null);
    }

    constructor(fields: SearchCommentsOutputFields | null) {
        super(fields);
    }

    isEmpty = (): boolean => {
        return !this.fields;
    }

    getTotal = (): number => {
        if (!this.fields) return 0;
        const fields: SearchCommentsOutputFields = this.fields as SearchCommentsOutputFields;
        return fields.content.total;
    }

    getComments = (): ReadonlyArray<CommentAndReport> => {
        if (!this.fields) return [];
        const fields: SearchCommentsOutputFields = this.fields as SearchCommentsOutputFields;
        return fields.content.comments;
    }

}
