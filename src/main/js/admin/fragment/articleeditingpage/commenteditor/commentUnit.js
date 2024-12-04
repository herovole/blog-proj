
export class CommentUnit {

    static API_KEY_ID = "commentId";
    static API_KEY_TEXT = "commentText";
    static API_KEY_COUNTRY = "country";
    static API_KEY_IS_HIDDEN = "isHidden";
    static API_KEY_REFERRING_IDS = "referringCommentIds";

    static fromHash(hash) {
        return new CommentUnit(
            hash[CommentUnit.API_KEY_ID],
            hash[CommentUnit.API_KEY_TEXT],
            hash[CommentUnit.API_KEY_COUNTRY],
            hash[CommentUnit.API_KEY_IS_HIDDEN],
            hash[CommentUnit.API_KEY_REFERRING_IDS]
        );
    }

    constructor(commentId, text, country, isHidden, referringIds=[], depth=0) {
        this.commentId = commentId;
        this.text = text;
        this.country = country;
        this.isHidden = isHidden;
        this.referringIds = referringIds;
        this.depth = depth;
    }

    getLatestReferringId() {
        if(this.referringIds.length == 0) {
            return -1;
        }
        return Math.max(...this.referringIds);
    }

    applyDepth(depth) {
        return new CommentUnit(
            this.commentId,
            this.text,
            this.country,
            this.isHidden,
            this.referringIds,
            depth
        );
    }

}