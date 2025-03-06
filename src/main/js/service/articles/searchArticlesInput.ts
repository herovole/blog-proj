import {format} from "date-fns";

export class SearchArticlesInput {

    static byDefault() {
        return new SearchArticlesInput(
            10,
            1,
            true,
            null,
            null,
            ""
        );
    }

    itemsPerPage: number;
    page: number;
    isPublished: boolean;
    dateFrom: Date | null;
    dateTo: Date | null;
    keywords: string;

    constructor(
        itemsPerPage: number,
        page: number,
        isPublished: boolean,
        dateFrom: Date | null,
        dateTo: Date | null,
        keywords: string
    ) {
        this.itemsPerPage = itemsPerPage;
        this.page = page;
        this.isPublished = isPublished;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.keywords = keywords;
    }

    appendItemsPerPage = (itemsPerPage: number): SearchArticlesInput => {
        return new SearchArticlesInput(
            itemsPerPage,
            this.page,
            this.isPublished,
            this.dateFrom,
            this.dateTo,
            this.keywords
        );
    }

    appendPage = (page: number): SearchArticlesInput => {
        return new SearchArticlesInput(
            this.itemsPerPage,
            page,
            this.isPublished,
            this.dateFrom,
            this.dateTo,
            this.keywords,
        );
    }

    appendDateFrom = (dateFrom: Date | null): SearchArticlesInput => {
        return new SearchArticlesInput(
            this.itemsPerPage,
            this.page,
            this.isPublished,
            dateFrom,
            this.dateTo,
            this.keywords
        );
    }

    appendDateTo = (dateTo: Date | null): SearchArticlesInput => {
        return new SearchArticlesInput(
            this.itemsPerPage,
            this.page,
            this.isPublished,
            this.dateFrom,
            dateTo,
            this.keywords
        );
    }

    appendKeywords = (keywords: string): SearchArticlesInput => {
        return new SearchArticlesInput(
            this.itemsPerPage,
            this.page,
            this.isPublished,
            this.dateFrom,
            this.dateTo,
            keywords
        );
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "itemsPerPage": this.itemsPerPage.toString(),
            "page": this.page.toString(),
            "isPublished": this.isPublished.toString(),
            "dateFrom": this.dateFrom ? format(this.dateFrom, "yyyyMMdd") : "",
            "dateTo": this.dateTo ? format(this.dateTo, "yyyyMMdd") : "",
            "keywords": this.keywords,
        };
    };
}