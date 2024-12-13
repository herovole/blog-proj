import { CommentUnit } from '../../../domain/commentUnit';


export class UserCommentUnit extends CommentUnit  {

    static API_KEY_ID = "id";
    static API_KEY_COMMENT_ID = "commentId";
    static API_KEY_TEXT = "commentText";
    static API_KEY_IS_HIDDEN = "isHidden";
    static API_KEY_REFERRING_IDS = "referringCommentIds";
    static API_KEY_LIKES = "likes";
    static API_KEY_DISLIKES = "dislikes";
    static API_KEY_DAILY_ID = "dailyId";
    static API_KEY_POST_TIMESTAMP = "postTimestamp";

    static fromHash(hash) {
        return new UserCommentUnit(
            hash[CommentUnit.API_KEY_ID],
            hash[CommentUnit.API_KEY_COMMENT_ID],
            hash[CommentUnit.API_KEY_TEXT],
            hash[CommentUnit.API_KEY_IS_HIDDEN],
            hash[CommentUnit.API_KEY_REFERRING_IDS],
            hash[CommentUnit.API_KEY_LIKES],
            hash[CommentUnit.API_KEY_DISLIKES],
            hash[CommentUnit.API_KEY_DAILY_ID],
            hash[CommentUnit.API_KEY_POST_TIMESTAMP],
        );
    }

    constructor(id, commentId, text, country, isHidden, referringIds=[], depth=0, likes, dislikes, dailyId, postTimestamp) {
        this.id = id;
        this.commentId = commentId;
        this.text = text;
        this.isHidden = isHidden;
        this.referringIds = referringIds;
        this.depth = depth;
        this.likes = likes;
        this.dislikes = dislikes;
        this.dailyId = dailyId;
        this.postTimestamp = postTimestamp;
    }

    getInArticleCommentId() { return this.commentId; };
    getReferringCommentIds() { return this.referringIds; };
    getDepth() { return this.depth; };
    applyDepth(depth) {
        return new UserCommentUnit(
            this.id,
            this.commentId,
            this.text,
            this.isHidden,
            this.referringIds,
            depth,
            this.likes,
            this.dislikes,
            this.dailyId,
            this.postTimestamp
        );
    }

}