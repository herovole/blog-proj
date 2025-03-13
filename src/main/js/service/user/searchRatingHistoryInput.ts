export class SearchRatingHistoryInput {
    private readonly articleId: string;

    constructor(articleId: string | undefined) {
        if (!articleId) throw new Error("article ID is not defined.");
        this.articleId = articleId;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "articleId": encodeURIComponent(this.articleId.toString()),
        };
    };

}