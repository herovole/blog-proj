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
            "itemsPerPage": this.itemsPerPage.toString(),
            "page": this.page.toString(),
            "isPublished": this.isPublished ? "1" : "0",
            "dateFrom": this.dateFrom ? format(this.dateFrom, "yyyyMMdd") : "",
            "dateTo": this.dateTo ? format(this.dateTo, "yyyyMMdd") : "",
            "keywords": this.keywords,
            "requiresAuth": this.requiresAuth ? "1" : "0",
        };
    };
}