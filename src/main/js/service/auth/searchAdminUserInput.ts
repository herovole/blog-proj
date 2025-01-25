export class SearchAdminUserInput {

    private readonly page: number;
    private readonly itemsPerPage: number;
    private readonly isDetailed: boolean;

    constructor(page: number, itemsPerPage: number, isDetailed: boolean) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        this.isDetailed = isDetailed;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "page": this.page.toString(),
            "itemsPerPage": this.itemsPerPage.toString(),
            "isDetailed": this.isDetailed ? "1" : "0",
        };
    };
}