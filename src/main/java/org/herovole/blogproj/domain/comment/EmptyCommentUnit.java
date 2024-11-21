package org.herovole.blogproj.domain.comment;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.herovole.blogproj.domain.IntegerId;

@ToString
@EqualsAndHashCode
public class EmptyCommentUnit implements CommentUnit {
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public IntegerId getCommentId() {
        return IntegerId.empty();
    }

    @Override
    public boolean hasSameCommentId(CommentUnit that) {
        return false;
    }

    @Override
    public boolean hasSameContent(CommentUnit that) {
        return false;
    }
}
