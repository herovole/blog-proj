export class FindArticleInput {
    articleId: string;
    requiresAuth: boolean;

    constructor(articleId: string | undefined, requiresAuth: boolean) {
        if (!articleId) throw new Error("article ID is not defined.");
        this.articleId = articleId;
        this.requiresAuth = requiresAuth;
    }

    getArticleId(): string {
        return this.articleId;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "articleId": encodeURIComponent(this.articleId.toString()),
            "requiresAuth": encodeURIComponent(this.requiresAuth.toString()),
        };
    };
}