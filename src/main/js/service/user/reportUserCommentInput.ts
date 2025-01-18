export class ReportUserCommentInput {
    commentSerialNumber: number;
    reportingText: string | undefined;
    verificationToken: string;

    constructor(commentSerialNumber: number,
                reportingText: string | undefined,
                verificationToken: string) {
        this.commentSerialNumber = commentSerialNumber;
        this.reportingText = reportingText;
        this.verificationToken = verificationToken;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "commentSerialNumber": this.commentSerialNumber.toString(),
            "text": this.reportingText ? this.reportingText : "",
            "botDetectionToken": this.verificationToken
        };
    };

    buildUrl(): string {
        return "/api/v1/usercomments/" + this.commentSerialNumber.toString() + "/reports";
    }
}