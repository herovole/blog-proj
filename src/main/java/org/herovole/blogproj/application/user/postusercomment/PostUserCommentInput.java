package org.herovole.blogproj.application.user.postusercomment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.HandleName;
import org.herovole.blogproj.domain.comment.RealUserCommentUnit;
import org.herovole.blogproj.domain.publicuser.DailyUserId;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Timestamp;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUserCommentInput {

    public static class Builder {
        private IPv4Address iPv4Address;
        private IntegerPublicUserId userId;
        private FormContent formContent;

        public Builder iPv4Address(IPv4Address iPv4Address) {
            this.iPv4Address = iPv4Address;
            return this;
        }

        public Builder userId(IntegerPublicUserId userId) {
            this.userId = userId;
            return this;
        }

        public Builder formContent(FormContent formContent) {
            this.formContent = formContent;
            return this;
        }

        public PostUserCommentInput build() {
            if (iPv4Address == null || userId == null || formContent == null) {
                throw new IllegalStateException(PostUserCommentInput.class.getSimpleName() + "Invalid building process.");
            }
            return new PostUserCommentInput(
                    iPv4Address,
                    userId,
                    IntegerId.fromFormContentArticleId(formContent),
                    HandleName.fromFormContentHandleName(formContent),
                    CommentText.fromFormContentCommentText(formContent)
            );
        }
    }

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

