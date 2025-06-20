package org.herovole.blogproj.domain.comment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.publicuser.DailyUserId;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.publicuser.PublicUserId;
import org.herovole.blogproj.domain.time.Date;
import org.herovole.blogproj.domain.time.Timestamp;

import java.security.NoSuchAlgorithmException;

@ToString
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RealUserCommentUnit implements CommentUnit {

    public static RealUserCommentUnit fromFormContent(FormContent formContent) {
        CommentText commentText = CommentText.fromFormContentCommentText(formContent);
        return RealUserCommentUnit.builder()
                .commentSerialNumber(IntegerId.empty())
                .commentId(IntegerId.fromFormContentCommentId(formContent))
                .articleId(IntegerId.fromFormContentArticleId(formContent))
                .handleName(HandleName.fromFormContentHandleName(formContent))
                .commentText(commentText)
                .isHidden(GenericSwitch.fromFormContentIsHidden(formContent))
                .referringCommentIds(commentText.scanReferringCommentIds())
                .build();
    }

    private final IntegerId commentSerialNumber;
    @EqualsAndHashCode.Include
    private final IntegerId commentId;

    @EqualsAndHashCode.Include
    private final IntegerId articleId;
    @EqualsAndHashCode.Include
    private final HandleName handleName;
    @EqualsAndHashCode.Include
    private final CommentText commentText;
    @EqualsAndHashCode.Include
    private final GenericSwitch isHidden;
    private final IntegerIds referringCommentIds;
    private final int likes;
    private final int dislikes;
    private final DailyUserId dailyUserId;
    private final PublicUserId publicUserId;
    private final IPv4Address ip;
    private final Timestamp postTimestamp;

    @Override
    public boolean isEmpty() {
        return this.handleName.isEmpty() && this.commentText.isEmpty();
    }

    @Override
    public IntegerId getSerialNumber() {
        return this.commentSerialNumber;
    }

    @Override
    public boolean hasSameCommentId(CommentUnit that) {
        if (that.isEmpty()) return false;
        return this.commentId.equals(((RealUserCommentUnit) that).commentId);
    }

    @Override
    public boolean precedes(CommentUnit that) {
        if (that.isEmpty()) return false;
        return this.commentId.precedes(((RealUserCommentUnit) that).commentId);
    }

    @Override
    public boolean hasSameContent(CommentUnit that) {
        if (that.isEmpty()) return false;
        RealUserCommentUnit it = (RealUserCommentUnit) that;
        return this.commentText.equals(it.commentText)
                && this.isHidden.equals(it.isHidden)
                && this.referringCommentIds.equals(it.referringCommentIds);
    }

    @Override
    public IntegerId getLatestReferredId() {
        return this.referringCommentIds.getLargest();
    }

    @Override
    public IntegerPublicUserId getIntegerPublicUserId() {
        return (IntegerPublicUserId) this.publicUserId;
    }

    @Override
    public CommentUnit appendDailyUserId(DailyUserIdFactory algorithm) throws NoSuchAlgorithmException {
        return RealUserCommentUnit.builder()
                .commentSerialNumber(this.commentSerialNumber)
                .commentId(this.commentId)
                .articleId(this.articleId)
                .handleName(this.handleName)
                .commentText(this.commentText)
                .isHidden(this.isHidden)
                .referringCommentIds(this.referringCommentIds)
                .likes(this.likes)
                .dislikes(this.dislikes)
                .dailyUserId(algorithm.generate(this.ip, Date.today()))
                .publicUserId(this.publicUserId)
                .ip(this.ip)
                .postTimestamp(this.postTimestamp)
                .build();
    }

    @Override
    public CommentUnit maskPrivateItems() {
        return RealUserCommentUnit.builder()
                .commentSerialNumber(this.commentSerialNumber)
                .commentId(this.commentId)
                .articleId(this.articleId)
                .handleName(this.handleName)
                .commentText(this.isHidden.isTrue() ? CommentText.hidden() : this.commentText)
                .isHidden(this.isHidden)
                .referringCommentIds(this.referringCommentIds)
                .likes(this.likes)
                .dislikes(this.dislikes)
                .dailyUserId(this.dailyUserId)
                .publicUserId(IntegerPublicUserId.empty())
                .ip(IPv4Address.empty())
                .postTimestamp(this.postTimestamp)
                .build();
    }

    @Override
    public int getDepth() {
        return 0;
    }

    @Override
    public CommentUnit appendDepth(int depth) {
        return new CommentUnitWithDepth(this, depth);
    }

    @Override
    public boolean isHidden() {
        return this.isHidden.isTrue();
    }

    @Override
    public CommentUnit.Json toJson() {
        return new Json(
                this.commentSerialNumber.longMemorySignature(),
                this.commentId.intMemorySignature(),
                this.articleId.intMemorySignature(),
                this.handleName.memorySignature(),
                this.commentText.memorySignature(),
                this.isHidden.memorySignature(),
                this.referringCommentIds.toIntMemorySignature(),
                this.likes,
                this.dislikes,
                this.dailyUserId.memorySignature(),
                this.publicUserId.longMemorySignature(),
                this.ip.toRegularFormat(),
                this.postTimestamp.letterSignatureFrontendDisplay()
        );
    }

    record Json(
            long commentSerialNumber,
            Integer commentId,
            Integer articleId,
            String handleName,
            String commentText,
            boolean isHidden,
            int[] referringCommentIds,
            int likes,
            int dislikes,
            String dailyUserId,
            Long publicUserId,
            String ip,
            String postTimestamp
    ) implements CommentUnit.Json {
    }
}
