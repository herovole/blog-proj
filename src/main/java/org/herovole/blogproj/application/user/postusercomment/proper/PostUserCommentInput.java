package org.herovole.blogproj.application.user.postusercomment.proper;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.HandleName;
import org.herovole.blogproj.domain.comment.RealUserCommentUnit;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.user.DailyUserId;
import org.herovole.blogproj.domain.user.IntegerPublicUserId;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUserCommentInput {

    private final IPv4Address iPv4Address;
    private final IntegerPublicUserId userId;

    private final IntegerId articleId;
    private final HandleName handleName;
    private final CommentText commentText;

    CommentUnit buildCommentUnit() {
        return RealUserCommentUnit.builder()
                .commentSerialNumber(IntegerId.empty())
                .commentId(IntegerId.empty())
                .articleId(articleId)
                .handleName(handleName)
                .commentText(commentText)
                .isHidden(GenericSwitch.negative())
                .referringCommentIds(commentText.scanReferringCommentIds())
                .likes(0)
                .dislikes(0)
                .dailyUserId(DailyUserId.empty())
                .publicUserId(userId)
                .ip(iPv4Address)
                .postTimestamp(Timestamp.now())
                .build();
    }
}

