package org.herovole.blogproj.domain.comment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.CommentText;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.tag.country.CountryCode;

@ToString
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RealSourceCommentUnit implements CommentUnit {

    public static RealSourceCommentUnit fromFormContent(FormContent formContent) {
        return RealSourceCommentUnit.builder()
                .commentSerialNumber(IntegerId.empty())
                .commentId(IntegerId.fromPostContentCommentId(formContent))
                .commentText(CommentText.fromFormContentCommentText(formContent))
                .country(CountryCode.fromPostContent(formContent))
                .isHidden(GenericSwitch.fromPostContentIsHidden(formContent))
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
    public CommentUnit.Json toJson() {
        return new Json(
                this.commentSerialNumber.longMemorySignature(),
                this.commentId.intMemorySignature(),
                this.commentText.memorySignature(),
                this.country.memorySignature(),
                this.isHidden.memorySignature(),
                this.referringCommentIds.toIntMemorySignature()
        );
    }

    record Json(
            long commentSerialNumber,
            Integer commentId,
            String commentText,
            String country,
            boolean isHidden,
            int[] referringCommentIds
    ) implements CommentUnit.Json {
    }

}
