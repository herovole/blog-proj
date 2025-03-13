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
            "page": encodeURIComponent(this.page.toString()),
            "itemsPerPage": encodeURIComponent(this.itemsPerPage.toString()),
            "requiresAuth": this.requiresAuth ? encodeURIComponent("1") : encodeURIComponent("0"),
        };
    };
}