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

    toPayloadHash(): string {
        return JSON.stringify({
            "commentSerialNumber": encodeURIComponent(this.commentSerialNumber.toString()),
            "text": this.reportingText ? encodeURIComponent(this.reportingText) : "",
            "botDetectionToken": encodeURIComponent(this.verificationToken)
        });
    };

    buildUrl(): string {
        return "/api/v1/usercomments/" + this.commentSerialNumber.toString() + "/reports";
    }
}