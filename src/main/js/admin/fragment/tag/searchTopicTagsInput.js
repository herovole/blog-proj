
export class SearchTopicTagsInput {

    static API_KEY_ITEMS = "itemsPerPage";
    static API_KEY_PAGE = "page";
    static API_KEY_IS_DETAILED = "isDetailed";

    static byDefault(formKey, isDetailed) {
        return new SearchTopicTagsInput(
            formKey,
            10,
            1,
            isDetailed
        );
    }

    constructor(
        formKey,
        itemsPerPage,
        page,
        isDetailed = false
    ) {
        this.formKey = formKey;
        this.itemsPerPage = itemsPerPage;
        this.page = page;
        this.isDetailed = isDetailed;
    }

    appendItemsPerPage = (itemsPerPage) => {
        return new SearchTopicTagsInput(
            this.formKey,
            itemsPerPage,
            this.page,
            this.isDetailed
        );
    }

    appendPage = (page) => {
        return new SearchTopicTagsInput(
            this.formKey,
            this.itemsPerPage,
            page,
            this.isDetailed
        );
    }

    toUrlSearchParams = () => {
        const hash = {};
        hash[this.formKey.append(SearchTopicTagsInput.API_KEY_ITEMS).toStringKey()] = this.itemsPerPage;
        hash[this.formKey.append(SearchTopicTagsInput.API_KEY_PAGE).toStringKey()] = this.page;
        hash[this.formKey.append(SearchTopicTagsInput.API_KEY_IS_DETAILED).toStringKey()] = this.isDetailed;
        return new URLSearchParams(Object.entries(hash));
    }
}