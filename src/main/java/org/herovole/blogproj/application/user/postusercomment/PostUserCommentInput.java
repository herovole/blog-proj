package org.herovole.blogproj.application.user.postusercomment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.HandleName;
import org.herovole.blogproj.domain.comment.RealUserCommentUnit;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.user.DailyUserId;
import org.herovole.blogproj.domain.user.UniversallyUniqueId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUserCommentInput {

    public static class Builder {
        private IPv4Address iPv4Address;
        private UniversallyUniqueId uuId;
        private FormContent formContent;

        public Builder setiPv4Address(IPv4Address iPv4Address) {
            this.iPv4Address = iPv4Address;
            return this;
        }

        public Builder setUuId(UniversallyUniqueId uuId) {
            this.uuId = uuId;
            return this;
        }

        public Builder setFormContent(FormContent formContent) {
            this.formContent = formContent;
            return this;
        }

        public PostUserCommentInput build() {
            if (iPv4Address == null || uuId == null || formContent == null) {
                throw new IllegalStateException(PostUserCommentInput.class.getSimpleName() + "Invalid building process.");
            }
            FormContent children = formContent.getChildren(API_KEY_PREFIX);
            return new PostUserCommentInput(
                    iPv4Address,
                    uuId,
                    IntegerId.fromFormContentArticleId(children),
                    HandleName.fromFormContentHandleName(children),
                    CommentText.fromFormContentCommentText(children)
            );
        }
    }

    private static final String API_KEY_PREFIX = "userComment";

    private final IPv4Address iPv4Address;
    private final UniversallyUniqueId uuId;

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
                .referringCommentIds(IntegerIds.empty())
                .likes(0)
                .dislikes(0)
                .dailyUserId(DailyUserId.empty())
                .uuId(uuId)
                .ip(iPv4Address)
                .postTimestamp(Timestamp.now())
                .build();
    }
}

