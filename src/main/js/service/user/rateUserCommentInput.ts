export class RateUserCommentInput {
    commentSerialNumber: number;
    rating: number;
    verificationToken: string;

    constructor(commentSerialNumber: number,
                rating: number,
                verificationToken: string) {
        this.commentSerialNumber = commentSerialNumber;
        this.rating = rating;
        this.verificationToken = verificationToken;
    }

    toPayloadHash(): string {
        return JSON.stringify({
            "commentSerialNumber": encodeURIComponent(this.commentSerialNumber.toString()),
            "rating": encodeURIComponent(this.rating.toString()),
            "botDetectionToken": encodeURIComponent(this.verificationToken)
        });
    };

    buildUrl(): string {
        return "/api/v1/usercomments/" + this.commentSerialNumber.toString() + "/rate";
    }
}