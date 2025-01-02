export class ArticleSummary {

    static API_KEY_ID = "articleId";
    static API_KEY_IMAGE = "imageName";
    static API_KEY_TITLE = "title";
    static API_KEY_TEXT = "text";
    static API_KEY_SOURCE_URL = "sourceUrl";
    static API_KEY_SOURCE_TITLE = "sourceTitle";
    static API_KEY_SOURCE_DATE = "sourceDate";
    static API_KEY_IS_PUBLISHED = "isPublished";
    static API_KEY_COUNTRIES = "countries";
    static API_KEY_TOPIC_TAGS = "topicTags";
    static API_KEY_EDITORS = "editors";
    static API_KEY_COUNT_ORIGINAL_COMMENTS = "sourceComments";
    static API_KEY_COUNT_USER_COMMENTS = "userComments";
    static API_KEY_REGISTRATION_TIMESTAMP = "registrationTimestamp";
    static API_KEY_LATEST_EDIT_TIMESTAMP = "latestEditTimestamp";

    static fromHash(hash) {
        return new ArticleSummary(
            hash[ArticleSummary.API_KEY_ID],
            hash[ArticleSummary.API_KEY_IMAGE],
            hash[ArticleSummary.API_KEY_TITLE],
            hash[ArticleSummary.API_KEY_TEXT],
            hash[ArticleSummary.API_KEY_SOURCE_URL],
            hash[ArticleSummary.API_KEY_SOURCE_TITLE],
            hash[ArticleSummary.API_KEY_SOURCE_DATE],
            hash[ArticleSummary.API_KEY_IS_PUBLISHED],
            hash[ArticleSummary.API_KEY_COUNTRIES],
            hash[ArticleSummary.API_KEY_TOPIC_TAGS],
            hash[ArticleSummary.API_KEY_EDITORS],
            hash[ArticleSummary.API_KEY_COUNT_ORIGINAL_COMMENTS],
            hash[ArticleSummary.API_KEY_COUNT_USER_COMMENTS],
            hash[ArticleSummary.API_KEY_REGISTRATION_TIMESTAMP],
            hash[ArticleSummary.API_KEY_LATEST_EDIT_TIMESTAMP]
        );
    }

    constructor(
        articleId,
        imageName, title, text,
        sourceUrl, sourceTitle, sourceDate,
        isPublished,
        countries = [],
        topicTags = [],
        editors = [],
        countOriginalComments,
        countUserComments,
        registrationTimestamp,
        latestEditTimestamp
    ) {
        this.articleId = articleId;
        this.imageName = imageName;
        this.title = title;
        this.text = text;
        this.sourceUrl = sourceUrl;
        this.sourceTitle = sourceTitle;
        this.sourceDate = sourceDate;
        this.isPublished = isPublished;
        this.countries = countries;
        this.topicTags = topicTags;
        this.editors = editors;
        this.countOriginalComments = countOriginalComments;
        this.countUserComments = countUserComments;
        this.registrationTimestamp = registrationTimestamp;
        this.latestEditTimestamp = latestEditTimestamp;
    }

    getSlicedTitle(lettersToPickUp) {
        return article.title ? article.title.slice(0, lettersToPickUp) : "";
    }

}