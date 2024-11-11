package org.herovole.blogproj.domain.comment;

import lombok.Builder;
import org.herovole.blogproj.domain.CommentText;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.PostContent;

@Builder
public class CommentUnit {

    public static CommentUnit fromPostContent(PostContent postContent) {

        return CommentUnit.builder()
                .commentId(IntegerId.fromPostContentCommentId(postContent))
                .commentText(CommentText.fromPostContentCommentText(postContent))
                .country()
                .isHidden(GenericSwitch.fromPostContentIsHidden(postContent))
                .referringCommentIds(IntegerIds.fromPostContentReferringCommentIds(postContent))
                .build();
    }

    private final IntegerId commentId;
    private final CommentText commentText;
    private final IntegerId country;
    private final GenericSwitch isHidden;
    private final IntegerIds referringCommentIds;
}
