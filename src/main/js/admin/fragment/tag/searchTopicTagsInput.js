
export class SearchTopicTagsInput {

    static API_KEY_ITEMS = "itemsPerPage";
    static API_KEY_PAGE = "page";

    static byDefault(formKey) {
        return new SearchTopicTagsInput(
            formKey,
            10,
            1
        );
    }

    constructor(
        formKey,
        itemsPerPage,
        page
    ) {
        this.formKey = formKey;
        this.itemsPerPage = itemsPerPage;
        this.page = page;
    }

    appendItemsPerPage = (itemsPerPage) => {
        return new SearchTopicTagsInput(
            this.formKey,
            itemsPerPage,
            this.page
        );
    }

    appendPage = (page) => {
        return new SearchTopicTagsInput(
            this.formKey,
            this.itemsPerPage,
            page
        );
    }

    toUrlSearchParams = () => {
        const hash = {};
        hash[this.formKey.append(SearchTopicTagsInput.API_KEY_ITEMS).toStringKey()] = this.itemsPerPage;
        hash[this.formKey.append(SearchTopicTagsInput.API_KEY_PAGE).toStringKey()] = this.page;
        return new URLSearchParams(Object.entries(hash));
    }
}