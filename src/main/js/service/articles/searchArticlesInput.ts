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
            null,
            null,
            isForAdmin
        );
    }

    static byDefaultOrGetParams(getParams : URLSearchParams, isForAdmin: boolean) {
        return new SearchArticlesInput(
            getParams.get("itemsPerPage") ? parseInt(getParams.get("itemsPerPage")!) : 10,
            getParams.get("page") ? parseInt(getParams.get("page")!) : 1,
            getParams.get("isPublished") ? getParams.get("isPublished") === "1" : true,
            getParams.get("dateFrom") ? new Date(getParams.get("dateFrom")!) : null,
            getParams.get("dateTo") ? new Date(getParams.get("dateTo")!) : null,
            getParams.get("keywords") ? getParams.get("keywords")! : "",
            isForAdmin
        );
    }

    itemsPerPage: number;
    page: number;
    isPublished: boolean;
    dateFrom: Date | null;
    dateTo: Date | null;
    keywords: string;
    topicTag: string | null;
    countryTag: string | null;
    requiresAuth: boolean;

    constructor(
        itemsPerPage: number,
        page: number,
        isPublished: boolean,
        dateFrom: Date | null,
        dateTo: Date | null,
        keywords: string,
        topicTag: string | null,
        countryTag: string | null,
        requiresAuth: boolean
    ) {
        this.itemsPerPage = itemsPerPage;
        this.page = page;
        this.isPublished = isPublished;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.keywords = keywords;
        this.topicTag = topicTag;
        this.countryTag = countryTag;
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
            this.topicTag,
            this.countryTag,
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
            "topicTagId": encodeURIComponent(this.topicTag ? this.topicTag : "-"),
            "country": encodeURIComponent(this.countryTag ? this.countryTag : "--"),
            "requiresAuth": this.requiresAuth ? encodeURIComponent("1") : encodeURIComponent("0"),
        };
    };
}