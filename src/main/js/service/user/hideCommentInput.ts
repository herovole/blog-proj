export class HideCommentInput {
    commentSerialNumber: number;
    hides: boolean;
    botDetectionToken: string;

    constructor(commentSerialNumber: number,
                hides: boolean,
                botDetectionToken: string) {
        this.commentSerialNumber = commentSerialNumber;
        this.hides = hides;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "commentSerialNumber": this.commentSerialNumber.toString(),
            "isHidden": this.hides.toString(),
            "botDetectionToken": this.botDetectionToken,
        };
    };

}