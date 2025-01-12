export class SearchTagsInput {
    page: number;
    itemsPerPage: number;
    isDetailed: boolean;

    constructor(page: number, itemsPerPage: number, isDetailed: boolean) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        this.isDetailed = isDetailed;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "page": this.page.toString(),
            "itemsPerPage": this.itemsPerPage.toString(),
            "isDetailed": this.isDetailed.toString()
        };
    };
}