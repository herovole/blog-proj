export class SearchImagesInput {
    page: number;
    itemsPerPage: number;

    constructor(page: number, itemsPerPage: number) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "page": this.page.toString(),
            "itemsPerPage": this.itemsPerPage.toString(),
            "requiresAuth": "true"
        };
    };
}