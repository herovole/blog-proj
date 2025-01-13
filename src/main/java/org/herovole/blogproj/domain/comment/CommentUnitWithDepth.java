package org.herovole.blogproj.domain.comment;

import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
public class CommentUnitWithDepth implements CommentUnit {

    private final CommentUnit body;
    private final int depth;

    @Override
    public boolean isEmpty() {
        return this.body.isEmpty();
    }

    @Override
    public IntegerId getCommentId() {
        return this.body.getCommentId();
    }

    @Override
    public IntegerId getArticleId() {
        return this.body.getArticleId();
    }

    @Override
    public HandleName getHandleName() {
        return this.body.getHandleName();
    }

    @Override
    public CommentText getCommentText() {
        return this.body.getCommentText();
    }

    @Override
    public IntegerId getLatestReferredId() {
        return this.body.getLatestReferredId();
    }

    @Override
    public boolean hasSameCommentId(CommentUnit that) {
        return this.body.hasSameCommentId(that);
    }

    @Override
    public boolean hasSameContent(CommentUnit that) {
        return this.body.hasSameContent(that);
    }

    @Override
    public CommentUnit appendDailyUserId(DailyUserIdFactory algorithm) throws NoSuchAlgorithmException {
        return new CommentUnitWithDepth(this.body.appendDailyUserId(algorithm), this.depth);
    }

    @Override
    public CommentUnit maskPrivateItems() {
        return new CommentUnitWithDepth(this.body.maskPrivateItems(), this.depth);
    }

    @Override
    public int getDepth() {
        return this.depth;
    }

    @Override
    public CommentUnit appendDepth(int depth) {
        return new CommentUnitWithDepth(this.body, depth);
    }

    @Override
    public boolean isHidden() {
        return this.body.isHidden();
    }

    @Override
    public Json toJson() {
        return new Json(this.body.toJson(), this.depth);
    }

    record Json(
            CommentUnit.Json body,
            int depth
    ) implements CommentUnit.Json {
    }

}
