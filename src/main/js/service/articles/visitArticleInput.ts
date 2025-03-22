export class VisitArticleInput {
    articleId: string ;

    constructor(articleId: string ) {
        this.articleId = articleId;
    }

    getArticleId(): string {
        return encodeURIComponent(this.articleId);
    }

}