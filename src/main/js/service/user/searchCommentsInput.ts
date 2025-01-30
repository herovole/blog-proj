import {format} from "date-fns";

export class SearchCommentsInput {

    static byDefault() {
        return new SearchCommentsInput(
            10,
            1,
            null,
            null,
            "",
            true,
            true,
            true
        );
    }

    itemsPerPage: number;
    page: number;
    dateFrom: Date | null;
    dateTo: Date | null;
    keywords: string;
    hasReports: boolean;
    hasUnhandledReports: boolean;
    requiresAuth: boolean;

    constructor(
        itemsPerPage: number,
        page: number,
        dateFrom: Date | null,
        dateTo: Date | null,
        keywords: string,
        hasReports: boolean,
        hasUnaddressedReports: boolean,
        requiresAuth: boolean,
    ) {
        this.itemsPerPage = itemsPerPage;
        this.page = page;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.keywords = keywords;
        this.hasReports = hasReports;
        this.hasUnhandledReports = hasUnaddressedReports;
        this.requiresAuth = requiresAuth;
    }

    appendPage(page: number): SearchCommentsInput {
        return new SearchCommentsInput(
            this.itemsPerPage,
            page,
            this.dateFrom,
            this.dateTo,
            this.keywords,
            this.hasReports,
            this.hasUnhandledReports,
            this.requiresAuth
        );
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "itemsPerPage": this.itemsPerPage.toString(),
            "page": this.page.toString(),
            "dateFrom": this.dateFrom ? format(this.dateFrom, "yyyyMMdd") : "",
            "dateTo": this.dateTo ? format(this.dateTo, "yyyyMMdd") : "",
            "keywords": this.keywords,
            "hasReports": this.hasReports.toString(),
            "hasUnaddressedReports": this.hasUnhandledReports.toString(),
            "requiresAuth": this.requiresAuth.toString()
        };
    };
}