import {format} from "date-fns";
import {OrderBy, OrderByEnum} from "../../domain/articlelist/orderBy";

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
            null,
            isForAdmin
        );
    }

    static byDefaultOrGetParams(getParams: URLSearchParams, isForAdmin: boolean) {
        return new SearchArticlesInput(
            getParams.get("itemsPerPage") ? parseInt(getParams.get("itemsPerPage")!) : 10,
            getParams.get("page") ? parseInt(getParams.get("page")!) : 1,
            getParams.get("isPublished") ? getParams.get("isPublished") === "1" : true,
            getParams.get("dateFrom") ? new Date(getParams.get("dateFrom")!) : null,
            getParams.get("dateTo") ? new Date(getParams.get("dateTo")!) : null,
            getParams.get("keywords") ? getParams.get("keywords")! : "",
            getParams.get("topicTagId") ? getParams.get("topicTagId")! : null,
            getParams.get("country") ? getParams.get("country")! : null,
            getParams.get("orderBy") ? OrderBy.fromSignature(getParams.get("orderBy")!) : null,
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
    orderBy: OrderBy | null;
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
        orderBy: OrderBy | null,
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
        this.orderBy = orderBy;
        this.requiresAuth = requiresAuth;
    }

    isDefault(): boolean {
        return this.itemsPerPage == 10 &&
            this.page == 1 &&
            this.isPublished &&
            this.dateFrom == null &&
            this.dateTo == null &&
            this.keywords == "" &&
            this.topicTag == null &&
            this.countryTag == null &&
            this.orderBy == null;
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
            this.orderBy,
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
            "orderBy": encodeURIComponent(this.orderBy ? this.orderBy.getSignature() : new OrderBy(OrderByEnum.REGISTRATION_TIMESTAMP).getSignature()),
            "requiresAuth": this.requiresAuth ? encodeURIComponent("1") : encodeURIComponent("0"),
        };
    };
}