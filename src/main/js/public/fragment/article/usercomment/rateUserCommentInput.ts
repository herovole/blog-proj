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

    toPayloadHash(): { [key: string]: string } {
        return {
            "article.userCommentRating.commentSerialNumber": this.commentSerialNumber.toString(),
            "article.userCommentRating.rating": this.rating.toString(),
            "token": this.verificationToken
        };
    };

    buildUrl(): string {
        return "/api/v1/usercomments/" + this.commentSerialNumber.toString() + "/rate";
    }
}