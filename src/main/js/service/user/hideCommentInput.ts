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

    toPayloadHash(): string {
        return JSON.stringify({
            "commentSerialNumber": encodeURIComponent(this.commentSerialNumber.toString()),
            "isHidden": encodeURIComponent(this.isHidden.toString()),
            "requiresAuth": encodeURIComponent(this.requiresAuth.toString()),
            "botDetectionToken": encodeURIComponent(this.botDetectionToken),
        });
    };

}