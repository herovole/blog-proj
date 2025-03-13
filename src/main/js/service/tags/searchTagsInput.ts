export class SearchTagsInput {
    page: number;
    itemsPerPage: number;
    requiresAuth: boolean;

    constructor(page: number, itemsPerPage: number, requiresAuth: boolean) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        this.requiresAuth = requiresAuth;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "page": encodeURIComponent(this.page.toString()),
            "itemsPerPage": encodeURIComponent(this.itemsPerPage.toString()),
            "requiresAuth": encodeURIComponent(this.requiresAuth.toString())
        };
    };
}