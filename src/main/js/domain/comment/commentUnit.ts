
export interface CommentUnit {

    getInArticleCommentId();
    getReferringCommentIds();
    getDepth();
    applyDepth(depth);

    getLatestReferringId() {
        if(this.getReferringCommentIds().length == 0) {
            return -1;
        }
        return Math.max(...this.getReferringCommentIds());
    }

}