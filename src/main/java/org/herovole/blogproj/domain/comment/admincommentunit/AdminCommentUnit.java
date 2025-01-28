package org.herovole.blogproj.domain.comment.admincommentunit;

import lombok.Builder;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.article.ArticleTitle;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.HandleName;
import org.herovole.blogproj.domain.comment.reporting.Reporting;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;
import org.herovole.blogproj.domain.time.Timestamp;

import java.security.NoSuchAlgorithmException;

@Builder
public class AdminCommentUnit implements CommentUnit {
    private final CommentUnit userCommentUnit;
    private final Timestamp commentUserBannedUntil;
    private final Timestamp commentIpBannedUntil;
    private final ArticleTitle title;
    private final Reporting[] reportingUnits;

    @Override
    public boolean isEmpty() {
        return userCommentUnit.isEmpty();
    }

    @Override
    public IntegerId getCommentId() {
        return userCommentUnit.getCommentId();
    }

    @Override
    public IntegerId getArticleId() {
        return userCommentUnit.getArticleId();
    }

    @Override
    public HandleName getHandleName() {
        return userCommentUnit.getHandleName();
    }

    @Override
    public CommentText getCommentText() {
        return userCommentUnit.getCommentText();
    }

    @Override
    public IntegerId getLatestReferredId() {
        return userCommentUnit.getLatestReferredId();
    }

    @Override
    public boolean hasSameCommentId(CommentUnit that) {
        return userCommentUnit.hasSameCommentId(that);
    }

    @Override
    public boolean hasSameContent(CommentUnit that) {
        return userCommentUnit.hasSameContent(that);
    }

    @Override
    public CommentUnit appendDailyUserId(DailyUserIdFactory algorithm) throws NoSuchAlgorithmException {
        return userCommentUnit.appendDailyUserId(algorithm);
    }

    @Override
    public CommentUnit maskPrivateItems() {
        return userCommentUnit.maskPrivateItems();
    }

    @Override
    public int getDepth() {
        return userCommentUnit.getDepth();
    }

    @Override
    public CommentUnit appendDepth(int depth) {
        return userCommentUnit.appendDepth(depth);
    }

    @Override
    public boolean isHidden() {
        return userCommentUnit.isHidden();
    }

    public AdminCommentUnit append(Reporting[] reportingUnits) {
        return AdminCommentUnit.builder()
                .userCommentUnit(this.userCommentUnit)
                .commentUserBannedUntil(this.commentUserBannedUntil)
                .commentIpBannedUntil(this.commentIpBannedUntil)
                .title(this.title)
                .reportingUnits(reportingUnits)
                .build();
    }

    @Override
    public Json toJson() {
        return userCommentUnit.toJson();
    }
}
