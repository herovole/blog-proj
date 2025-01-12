export class FindArticleInput {
    articleId: string;

    constructor(articleId: string | undefined) {
        if (!articleId) throw new Error("article ID is not defined.");
        this.articleId = articleId;
    }

    getArticleId(): string {
        return this.articleId;
    }
}