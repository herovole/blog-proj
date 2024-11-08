
export class Article {

    static API_KEY_ID = "articleId";
    static API_KEY_IMAGE = "image";
    static API_KEY_TEXT = "text";
    static API_KEY_DATE = "date";
    static API_KEY_IS_PUBLISHED = "isPublished";
    static API_KEY_EDITORS = "editors";
    static API_KEY_ORIGINAL_COMMENTS = "originalComments";
    static API_KEY_USER_COMMENTS = "userComments";

    static fromJsonString(jsonString) {
        var parsedHash = JSON.parse(jsonString);
        return new CommentUnit(
            parsedHash[Article.API_KEY_ID],
            parsedHash[Article.API_KEY_IMAGE],
            parsedHash[Article.API_KEY_TEXT],
            parsedHash[Article.API_KEY_DATE],
            parsedHash[Article.API_KEY_IS_PUBLISHED],
            parsedHash[Article.API_KEY_EDITORS],
            CommentUnitList.fromHash(parsedHash[Article.API_KEY_ORIGINAL_COMMENTS]),
            CommentUnitList.fromHash(parsedHash[Article.API_KEY_USER_COMMENTS])
        );
    }

    constructor(
        articleId,
        image, text, date, isPublished,
        editors=[],
        originalComments = new CommentUnitList(),
        userComments = new CommentUnitList()
    ) {
        this.articleId = articleId;
        this.image = image;
        this.text = text;
        this.date = date;
        this.isPublished = isPublished;
        this.editors = editors;
        this.originalComments = originalComments;
        this.userComments = userComments;
    }

}