package org.herovole.blogproj.application.user.rateusercomment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.rating.Rating;
import org.herovole.blogproj.domain.comment.rating.RatingLog;
import org.herovole.blogproj.domain.comment.rating.RealRatingLog;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RateUserCommentInput {
    public static class Builder {
        private long commentSerialNumberConfirmation;
        private IPv4Address iPv4Address;
        private IntegerPublicUserId userId;
        private FormContent formContent;

        public Builder commentSerialNumberConfirmation(long commentSerialNumberConfirmation) {
            this.commentSerialNumberConfirmation = commentSerialNumberConfirmation;
            return this;
        }

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

        public RateUserCommentInput build() {
            if (iPv4Address == null || userId == null || formContent == null) {
                throw new IllegalStateException(RateUserCommentInput.class.getSimpleName() + "Invalid building process.");
            }
            IntegerId commentSerialNumber = IntegerId.fromFormContentCommentSerialNumber(formContent);
            if (commentSerialNumber.longMemorySignature() != commentSerialNumberConfirmation) {
                throw new IllegalArgumentException("Discrepancy between form and path commentSerialNumbers.");
            }
            formContent.println("comment post (parse 2)");
            return new RateUserCommentInput(
                    iPv4Address,
                    userId,
                    commentSerialNumber,
                    Rating.fromFormRating(formContent)
            );
        }
    }

    private final IPv4Address iPv4Address;
    private final IntegerPublicUserId userId;

    private final IntegerId commentSerialNumber;
    private final Rating rating;

    RatingLog buildRatingLog() {
        return RealRatingLog.builder()
                .logId(IntegerId.empty())
                .commentSerialNumber(commentSerialNumber)
                .publicUserId(userId)
                .ip(iPv4Address)
                .rating(rating)
                .build();
    }
}

