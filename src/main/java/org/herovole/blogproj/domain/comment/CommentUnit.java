package org.herovole.blogproj.domain.comment;

import lombok.Builder;
import org.herovole.blogproj.domain.CommentText;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.tag.CountryCode;

@Builder
public class CommentUnit {

    public static CommentUnit fromPostContent(PostContent postContent) {

        return CommentUnit.builder()
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
}
