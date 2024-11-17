package org.herovole.blogproj.domain.comment;

public class EmptyCommentUnit implements CommentUnit {
    @Override
    public boolean isEmpty() {
        return true;
    }
}
