package org.herovole.blogproj.domain.comment;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.user.DailyUserIdFactory;

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
    public IntegerId getArticleId() {
        return IntegerId.empty();
    }

    @Override
    public HandleName getHandleName() {
        return HandleName.empty();
    }

    @Override
    public CommentText getCommentText() {
        return CommentText.empty();
    }

    @Override
    public IntegerId getLatestReferredId() {
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

    @Override
    public Json toJson() {
        throw new UnsupportedOperationException();
    }


    @Override
    public CommentUnit appendDailyUserId(DailyUserIdFactory algorithm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CommentUnit maskPrivateItems() {
        return this;
    }

    @Override
    public int getDepth() {
        return 0;
    }

    @Override
    public CommentUnit appendDepth(int depth) {
        return this;
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
