package org.herovole.blogproj.application.postusercomment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.CommentText;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.RealUserCommentUnit;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.user.DailyUserId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUserCommentInput {

    private static final String API_KEY_PREFIX = "userComment";

    public static PostUserCommentInput fromFormContent(FormContent formContent) {
        FormContent children = formContent.getChildren(API_KEY_PREFIX);
        return new PostUserCommentInput(
                IntegerId.fromFormContentArticleId(children),
                CommentText.fromFormContentCommentText(children)
        );
    }

    private final IntegerId articleId;
    private final CommentText commentText;

    CommentUnit buildCommentUnit() {
        return RealUserCommentUnit.builder()
                .commentSerialNumber(IntegerId.empty())
                .commentId(IntegerId.empty())
                .articleId(articleId)
                .commentText(commentText)
                .isHidden(GenericSwitch.negative())
                .referringCommentIds(IntegerIds.empty())
                .likes(0)
                .dislikes(0)
                .dailyUserId(DailyUserId.empty())
                .postTimestamp(Timestamp.now())
                .build();
    }
}

