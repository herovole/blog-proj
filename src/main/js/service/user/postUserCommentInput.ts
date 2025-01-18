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

    toPayloadHash(): { [key: string]: string } {
        return {
            "articleId": this.articleId.toString(),
            "handleName": this.handleName ? this.handleName : "",
            "text": this.commentText ? this.commentText : "",
            "botDetectionToken": this.verificationToken
        };
    };

}