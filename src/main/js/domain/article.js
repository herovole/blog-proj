import {CommentUnitList} from "./commentUnitList";

export class Article {

    static API_KEY_ROOT = "article";
    static API_KEY_ID = "articleId";
    static API_KEY_IMAGE = "imageName";
    static API_KEY_TITLE = "title";
    static API_KEY_TEXT = "text";
    static API_KEY_COUNTRIES = "countries";
    static API_KEY_TOPIC_TAGS = "topicTags";
    static API_KEY_SOURCE_URL = "sourceUrl";
    static API_KEY_SOURCE_TITLE = "sourceTitle";
    static API_KEY_SOURCE_DATE = "sourceDate";
    static API_KEY_IS_PUBLISHED = "isPublished";
    static API_KEY_EDITORS = "editors";
    static API_KEY_ORIGINAL_COMMENTS = "sourceComments";
    static API_KEY_USER_COMMENTS = "userComments";
    static API_KEY_REGISTRATION_TIMESTAMP = "registrationTimestamp";
    static API_KEY_LATEST_EDIT_TIMESTAMP = "latestEditTimestamp";

    static fromHash(hash) {
        console.log("ARTICLE DATA" + JSON.stringify(hash));
        const content = hash[Article.API_KEY_ROOT];
        console.log("ARTICLE ID " + content[Article.API_KEY_ID]);
        return new Article (
            content[Article.API_KEY_ID],
            content[Article.API_KEY_IMAGE],
            content[Article.API_KEY_TITLE],
            content[Article.API_KEY_TEXT],
            content[Article.API_KEY_SOURCE_URL],
            content[Article.API_KEY_SOURCE_TITLE],
            content[Article.API_KEY_SOURCE_DATE],
            content[Article.API_KEY_IS_PUBLISHED],
            content[Article.API_KEY_COUNTRIES],
            content[Article.API_KEY_TOPIC_TAGS],
            content[Article.API_KEY_EDITORS],
            CommentUnitList.fromHash(content[Article.API_KEY_ORIGINAL_COMMENTS]),
            CommentUnitList.fromHash(content[Article.API_KEY_USER_COMMENTS]),
            content[Article.API_KEY_REGISTRATION_TIMESTAMP],
            content[Article.API_KEY_LATEST_EDIT_TIMESTAMP]
        );
    }

    constructor(
        articleId,
        imageName, title, text,
        sourceUrl, sourceTitle, sourceDate,
        isPublished,
        countries=[],
        topicTags=[],
        editors=[],
        originalComments = new CommentUnitList(),
        userComments = new CommentUnitList(),
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
        this.originalComments = originalComments;
        this.userComments = userComments;
        this.registrationTimestamp = registrationTimestamp;
        this.latestEditTimestamp = latestEditTimestamp;
    }

}