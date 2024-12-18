package org.herovole.blogproj.domain.comment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.CommentText;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.user.DailyUserId;
import org.herovole.blogproj.domain.user.UniversallyUniqueId;

@ToString
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RealUserCommentUnit implements CommentUnit {

    public static RealUserCommentUnit fromFormContent(FormContent formContent) {
        return RealUserCommentUnit.builder()
                .commentSerialNumber(IntegerId.empty())
                .commentId(IntegerId.fromFormContentCommentId(formContent))
                .articleId(IntegerId.fromFormContentArticleId(formContent))
                .commentText(CommentText.fromFormContentCommentText(formContent))
                .isHidden(GenericSwitch.fromPostContentIsHidden(formContent))
                .referringCommentIds(IntegerIds.fromPostContentReferringCommentIds(formContent))
                .build();
    }

    private final IntegerId commentSerialNumber;
    @EqualsAndHashCode.Include
    private final IntegerId commentId;

    @EqualsAndHashCode.Include
    private final IntegerId articleId;
    @EqualsAndHashCode.Include
    private final CommentText commentText;
    @EqualsAndHashCode.Include
    private final GenericSwitch isHidden;
    private final IntegerIds referringCommentIds;
    private final int likes;
    private final int dislikes;
    private final DailyUserId dailyUserId;
    private final UniversallyUniqueId uuId;
    private final IPv4Address ip;
    private final Timestamp postTimestamp;

    @Override
    public boolean isEmpty() {
        return this.commentId.isEmpty();
    }

    @Override
    public boolean hasSameCommentId(CommentUnit that) {
        if (that.isEmpty()) return false;
        return this.commentId.equals(((RealUserCommentUnit) that).commentId);
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
    public CommentUnit.Json toJson() {
        return new Json(
                this.commentSerialNumber.longMemorySignature(),
                this.commentId.intMemorySignature(),
                this.articleId.intMemorySignature(),
                this.commentText.memorySignature(),
                this.isHidden.memorySignature(),
                this.referringCommentIds.toIntMemorySignature(),
                this.likes,
                this.dislikes,
                this.dailyUserId.memorySignature(),
                this.postTimestamp.letterSignatureFrontendDisplay()
        );
    }

    record Json(
            long commentSerialNumber,
            Integer commentId,
            Integer articleId,
            String commentText,
            boolean isHidden,
            int[] referringCommentIds,
            int likes,
            int dislikes,
            String dailyUserId,
            String postTimestamp
    ) implements CommentUnit.Json {
    }

}
