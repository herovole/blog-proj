export class SearchImagesInput {
    page: number;
    itemsPerPage: number;

    constructor(page: number, itemsPerPage: number) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "page": encodeURIComponent(this.page.toString()),
            "itemsPerPage": encodeURIComponent(this.itemsPerPage.toString()),
            "requiresAuth": encodeURIComponent("true")
        };
    };
}