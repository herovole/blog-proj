
export class Article {

    static API_KEY_ID = "articleId";
    static API_KEY_IMAGE = "imageName";
    static API_KEY_TITLE = "title";
    static API_KEY_TEXT = "text";
    static API_KEY_SOURCE_URL = "sourceUrl";
    static API_KEY_SOURCE_TITLE = "sourceTitle";
    static API_KEY_SOURCE_DATE = "sourceDate";
    static API_KEY_IS_PUBLISHED = "isPublished";
    static API_KEY_EDITORS = "editors";
    static API_KEY_ORIGINAL_COMMENTS = "originalComments";
    static API_KEY_USER_COMMENTS = "userComments";

    static fromJsonString(jsonString) {
        var parsedHash = JSON.parse(jsonString);
        return new CommentUnit(
            parsedHash[Article.API_KEY_ID],
            parsedHash[Article.API_KEY_IMAGE],
            parsedHash[Article.API_KEY_TITLE],
            parsedHash[Article.API_KEY_TEXT],
            parsedHash[Article.API_KEY_SOURCE_URL],
            parsedHash[Article.API_KEY_SOURCE_TITLE],
            parsedHash[Article.API_KEY_SOURCE_DATE],
            parsedHash[Article.API_KEY_IS_PUBLISHED],
            parsedHash[Article.API_KEY_EDITORS],
            CommentUnitList.fromHash(parsedHash[Article.API_KEY_ORIGINAL_COMMENTS]),
            CommentUnitList.fromHash(parsedHash[Article.API_KEY_USER_COMMENTS])
        );
    }

    constructor(
        articleId,
        imageName, title, text,
        sourceUrl, sourceTitle, sourceDate,
        isPublished,
        editors=[],
        originalComments = new CommentUnitList(),
        userComments = new CommentUnitList()
    ) {
        this.articleId = articleId;
        this.imageName = imageName;
        this.title = title;
        this.text = text;
        this.sourceUrl = sourceUrl;
        this.sourceTitle = sourceTitle;
        this.sourceDate = sourceDate;
        this.isPublished = isPublished;
        this.editors = editors;
        this.originalComments = originalComments;
        this.userComments = userComments;
    }

}