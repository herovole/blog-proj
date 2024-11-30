
import { format } from "date-fns";

export class SearchArticlesInput {

    static API_KEY_ITEMS = "itemsPerPage";
    static API_KEY_PAGE = "page";
    static API_KEY_DATE_FROM = "dateFrom";
    static API_KEY_DATE_TO = "dateTo";
    static API_KEY_DATE_KEYWORDS = "keywords";

    static byDefault(formKey) {
        return new SearchArticlesInput(
            formKey,
            10,
            1,
            null,
            null,
            ""
        );
    }

    constructor(
        formKey,
        itemsPerPage,
        page,
        dateFrom,
        dateTo,
        keywords
    ) {
        this.formKey = formKey;
        this.itemsPerPage = itemsPerPage;
        this.page = page;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.keywords = keywords;
    }

    appendItemsPerPage = (itemsPerPage) => {
        return new SearchArticlesInput(
            this.formKey,
            itemsPerPage,
            this.page,
            this.dateFrom,
            this.dateTo,
            this.keywords
        );
    }

    appendPage = (page) => {
        return new SearchArticlesInput(
            this.formKey,
            this.itemsPerPage,
            page,
            this.dateFrom,
            this.dateTo,
            this.keywords
        );
    }

    appendDateFrom = (dateFrom) => {
        return new SearchArticlesInput(
            this.formKey,
            this.itemsPerPage,
            this.page,
            dateFrom,
            this.dateTo,
            this.keywords
        );
    }

    appendDateTo = (dateTo) => {
        return new SearchArticlesInput(
            this.formKey,
            this.itemsPerPage,
            this.page,
            this.dateFrom,
            dateTo,
            this.keywords
        );
    }

    appendKeywords = (keywords) => {
        return new SearchArticlesInput(
            this.formKey,
            this.itemsPerPage,
            this.page,
            this.dateFrom,
            this.dateTo,
            keywords
        );
    }

    toUrlSearchParams = () => {
        const hash = {};
        hash[this.formKey.append(SearchArticlesInput.API_KEY_ITEMS).toStringKey()] = this.itemsPerPage;
        hash[this.formKey.append(SearchArticlesInput.API_KEY_PAGE).toStringKey()] = this.page;
        hash[this.formKey.append(SearchArticlesInput.API_KEY_DATE_FROM).toStringKey()] = this.dateFrom ? format(this.dateFrom, "yyyyMMdd") : null;
        hash[this.formKey.append(SearchArticlesInput.API_KEY_DATE_TO).toStringKey()] = this.dateTo ? format(this.dateTo, "yyyyMMdd") : null;
        hash[this.formKey.append(SearchArticlesInput.API_KEY_DATE_KEYWORDS).toStringKey()] = this.keywords;
        return new URLSearchParams(Object.entries(hash));
    }
}