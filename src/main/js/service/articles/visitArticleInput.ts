export class VisitArticleInput {
    articleId: string | undefined;

    constructor(articleId: string | undefined) {
        this.articleId = articleId;
    }

    getArticleId(): string | undefined {
        return this.articleId;
    }

}