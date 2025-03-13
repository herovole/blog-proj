import {format} from "date-fns";

export class SearchArticlesInput {

    static byDefault(isForAdmin: boolean) {
        return new SearchArticlesInput(
            10,
            1,
            true,
            null,
            null,
            "",
            isForAdmin
        );
    }

    itemsPerPage: number;
    page: number;
    isPublished: boolean;
    dateFrom: Date | null;
    dateTo: Date | null;
    keywords: string;
    requiresAuth: boolean;

    constructor(
        itemsPerPage: number,
        page: number,
        isPublished: boolean,
        dateFrom: Date | null,
        dateTo: Date | null,
        keywords: string,
        requiresAuth: boolean
    ) {
        this.itemsPerPage = itemsPerPage;
        this.page = page;
        this.isPublished = isPublished;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.keywords = keywords;
        this.requiresAuth = requiresAuth;
    }

    appendPage(page: number): SearchArticlesInput {
        return new SearchArticlesInput(
            this.itemsPerPage,
            page,
            this.isPublished,
            this.dateFrom,
            this.dateTo,
            this.keywords,
            this.requiresAuth
        );
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "itemsPerPage": encodeURIComponent(this.itemsPerPage.toString()),
            "page": encodeURIComponent(this.page.toString()),
            "isPublished": this.isPublished ? encodeURIComponent("1") : encodeURIComponent("0"),
            "dateFrom": this.dateFrom ? encodeURIComponent(format(this.dateFrom, "yyyyMMdd")) : "",
            "dateTo": this.dateTo ? encodeURIComponent(format(this.dateTo, "yyyyMMdd")) : "",
            "keywords": encodeURIComponent(this.keywords),
            "requiresAuth": this.requiresAuth ? encodeURIComponent("1") : encodeURIComponent("0"),
        };
    };
}