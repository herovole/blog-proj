package org.herovole.blogproj.domain.comment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.CommentText;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.tag.CountryCode;

@ToString
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RealCommentUnit implements CommentUnit {

    public static RealCommentUnit fromPostContent(PostContent postContent) {
        return RealCommentUnit.builder()
                .commentSerialNumber(IntegerId.empty())
                .commentId(IntegerId.fromPostContentCommentId(postContent))
                .commentText(CommentText.fromPostContentCommentText(postContent))
                .country(CountryCode.fromPostContent(postContent))
                .isHidden(GenericSwitch.fromPostContentIsHidden(postContent))
                .referringCommentIds(IntegerIds.fromPostContentReferringCommentIds(postContent))
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
        return false;
    }

    @Override
    public boolean hasSameCommentId(CommentUnit that) {
        if (that.isEmpty()) return false;
        return this.commentId.equals(((RealCommentUnit) that).commentId);
    }

    @Override
    public boolean hasSameContent(CommentUnit that) {
        if (that.isEmpty()) return false;
        RealCommentUnit it = (RealCommentUnit) that;
        return this.commentText.equals(it.commentText)
                && this.country.equals(it.country)
                && this.isHidden.equals(it.isHidden)
                && this.referringCommentIds.equals(it.referringCommentIds);
    }
}
