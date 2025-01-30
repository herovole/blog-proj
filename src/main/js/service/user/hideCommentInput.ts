export class HideCommentInput {
    commentSerialNumber: number;
    isHidden: boolean;
    requiresAuth: boolean;
    botDetectionToken: string;

    constructor(commentSerialNumber: number,
                hides: boolean,
                requiresAuth: boolean,
                botDetectionToken: string) {
        this.commentSerialNumber = commentSerialNumber;
        this.isHidden = hides;
        this.requiresAuth = requiresAuth;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "commentSerialNumber": this.commentSerialNumber.toString(),
            "isHidden": this.isHidden.toString(),
            "requiresAuth": this.requiresAuth.toString(),
            "botDetectionToken": this.botDetectionToken,
        };
    };

}