
export class CommentUnit {

    static API_KEY_ID = "commentId";
    static API_KEY_TEXT = "text";
    static API_KEY_COUNTRY = "country";
    static API_KEY_IS_HIDDEN = "isHidden";

    static fromJsonString(jsonString) {
        var parsedHash = JSON.parse(jsonString);
        return new CommentUnit(
            parsedHash[TagUnit.API_KEY_ID],
            parsedHash[TagUnit.API_KEY_TEXT],
            parsedHash[TagUnit.API_KEY_COUNTRY],
            parsedHash[TagUnit.API_KEY_IS_HIDDEN]
        );
    }

    constructor(commentId, text, country, isHidden) {
        this.commentId = commentId;
        this.text = text;
        this.country = country;
        this.isHidden = isHidden;
    }

}