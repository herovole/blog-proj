package org.herovole.blogproj.domain.comment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.tag.country.CountryCode;
import org.herovole.blogproj.domain.time.Timestamp;

@ToString
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RealSourceCommentUnit implements CommentUnit {

    public static RealSourceCommentUnit fromFormContent(FormContent formContent) {
        return RealSourceCommentUnit.builder()
                .commentSerialNumber(IntegerId.fromFormContentCommentSerialNumber(formContent))
                .commentId(IntegerId.fromFormContentCommentId(formContent))
                .commentText(CommentText.fromFormContentCommentText(formContent))
                .country(CountryCode.fromPostContent(formContent))
                .isHidden(GenericSwitch.fromFormContentIsHidden(formContent))
                .referringCommentIds(IntegerIds.fromPostContentReferringCommentIds(formContent))
                .build();
    }

    private final IntegerId commentSerialNumber;
    @EqualsAndHashCode.Include
    private final IntegerId commentId;
    @EqualsAndHashCode.Include
    private final CommentText commentText;
    @EqualsAndHashCode.Include
    private final CountryCode country;
    @EqualsAndHashCode.Include
    private final GenericSwitch isHidden;
    private final IntegerIds referringCommentIds;

    @Override
    public boolean isEmpty() {
        return this.commentId.isEmpty();
    }

    @Override
    public IntegerId getSerialNumber() {
        return this.commentSerialNumber;
    }

    @Override
    public IntegerId getArticleId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HandleName getHandleName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IntegerId getLatestReferredId() {
        return this.referringCommentIds.getLargest();
    }

    @Override
    public IntegerPublicUserId getIntegerPublicUserId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Timestamp getPostTimestamp() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasSameCommentId(CommentUnit that) {
        if (that.isEmpty()) return false;
        return this.commentId.equals(((RealSourceCommentUnit) that).commentId);
    }

    @Override
    public boolean hasSameContent(CommentUnit that) {
        if (that.isEmpty()) return false;
        RealSourceCommentUnit it = (RealSourceCommentUnit) that;
        return this.commentText.equals(it.commentText)
                && this.country.equals(it.country)
                && this.isHidden.equals(it.isHidden)
                && this.referringCommentIds.equals(it.referringCommentIds);
    }


    @Override
    public CommentUnit appendDailyUserId(DailyUserIdFactory algorithm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CommentUnit maskPrivateItems() {
        return builder()
                .commentSerialNumber(this.commentSerialNumber)
                .commentId(this.commentId)
                .commentText(this.isHidden.isTrue() ? CommentText.hidden() : this.commentText)
                .country(this.country)
                .isHidden(this.isHidden)
                .referringCommentIds(this.referringCommentIds)
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
                this.commentSerialNumber.isEmpty() ? null : this.commentSerialNumber.longMemorySignature(),
                this.commentId.intMemorySignature(),
                this.commentText.memorySignature(),
                this.country.memorySignature(),
                this.isHidden.isEmpty(),
                this.referringCommentIds.toIntMemorySignature()
        );
    }

    record Json(
            Long commentSerialNumber,
            Integer commentId,
            String commentText,
            String country,
            boolean isHidden,
            int[] referringCommentIds
    ) implements CommentUnit.Json {
    }

}
