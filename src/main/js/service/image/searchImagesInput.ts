export class SearchImagesInput {
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
            "page": this.page.toString(),
            "itemsPerPage": this.itemsPerPage.toString(),
            "requiresAuth": this.requiresAuth.toString()
        };
    };
}