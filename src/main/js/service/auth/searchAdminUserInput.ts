export class SearchAdminUserInput {

    private readonly page: number;
    private readonly itemsPerPage: number;
    private readonly requiresAuth: boolean;

    constructor(page: number, itemsPerPage: number, requiresAuth: boolean) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        this.requiresAuth = requiresAuth;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "page": this.page.toString(),
            "itemsPerPage": this.itemsPerPage.toString(),
            "requiresAuth": this.requiresAuth ? "1" : "0",
        };
    };
}