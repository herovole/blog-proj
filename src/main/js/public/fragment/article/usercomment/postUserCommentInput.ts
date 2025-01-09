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
            "article.userCommentForm.articleId": this.articleId.toString(),
            "article.userCommentForm.handleName": this.handleName ? this.handleName : "",
            "article.userCommentForm.text": this.commentText ? this.commentText : "",
            "token": this.verificationToken
        };
    };

    buildUrl(): string {
        return "/api/v1/usercomments";
    }
}