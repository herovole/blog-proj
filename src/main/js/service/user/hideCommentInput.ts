export class HideCommentInput {
    commentSerialNumber: number;
    isHidden: boolean;
    botDetectionToken: string;

    constructor(commentSerialNumber: number,
                hides: boolean,
                botDetectionToken: string) {
        this.commentSerialNumber = commentSerialNumber;
        this.isHidden = hides;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "commentSerialNumber": this.commentSerialNumber.toString(),
            "isHidden": this.isHidden.toString(),
            "botDetectionToken": this.botDetectionToken,
        };
    };

}