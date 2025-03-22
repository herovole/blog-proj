export class PostUserCommentInput {
    articleId: number;
    handleName: string | undefined;
    commentText: string | undefined;
    verificationToken: string;

    constructor(articleId: number,
                handleName: string,
                commentText: string,
                verificationToken: string) {
        this.articleId = articleId;
        this.handleName = handleName;
        this.commentText = commentText;
        this.verificationToken = verificationToken;
    }

    toPayloadHash(): string {
        return JSON.stringify({
            "articleId": encodeURIComponent(this.articleId.toString()),
            "handleName": this.handleName ? encodeURIComponent(this.handleName) : "",
            "text": this.commentText ? encodeURIComponent(this.commentText) : "",
            "botDetectionToken": encodeURIComponent(this.verificationToken)
        });
    };

}