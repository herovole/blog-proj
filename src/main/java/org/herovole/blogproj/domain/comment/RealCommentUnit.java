package org.herovole.blogproj.domain.comment;

import lombok.Builder;
import lombok.ToString;
import org.herovole.blogproj.domain.CommentText;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.tag.CountryCode;

@ToString
@Builder
public class RealCommentUnit implements CommentUnit {

    public static RealCommentUnit fromPostContent(PostContent postContent) {
        return RealCommentUnit.builder()
                .commentId(IntegerId.fromPostContentCommentId(postContent))
                .commentText(CommentText.fromPostContentCommentText(postContent))
                .country(CountryCode.fromPostContent(postContent))
                .isHidden(GenericSwitch.fromPostContentIsHidden(postContent))
                .referringCommentIds(IntegerIds.fromPostContentReferringCommentIds(postContent))
                .build();
    }

    private final IntegerId commentId;
    private final CommentText commentText;
    private final CountryCode country;
    private final GenericSwitch isHidden;
    private final IntegerIds referringCommentIds;

    @Override
    public boolean isEmpty() {
        return false;
    }
}
