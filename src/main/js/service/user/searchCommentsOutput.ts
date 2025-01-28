import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";
import {UserCommentUnit} from "../../domain/comment/userCommentUnit";

export interface SearchCommentsOutputFields extends BasicApiResultFields {
    content: {
        commentUnits: ReadonlyArray<CommentAndReport>;
        total: number;
    };
}

export interface CommentAndReport {
    commentUnit: UserCommentUnit,
    userBannedUntil: string;
    hasUserBanned: boolean;
    ipBannedUntil: string;
    hasIpBanned: boolean;
    title: string
    reportingUnits: ReadonlyArray<ReportUnit>
}

export interface ReportUnit {
    reporting: {
        logId: number,
        commentSerialNumber:number,
        userId: number,
        ip: string,
        text: string,
        isHandled: boolean,
        reportTimestamp: string,
    }
    userBannedUntil: string;
    hasUserBanned: boolean;
    ipBannedUntil: string;
    hasIpBanned: boolean;
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
        return fields.content.commentUnits;
    }

}
