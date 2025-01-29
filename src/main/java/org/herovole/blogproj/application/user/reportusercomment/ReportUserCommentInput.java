package org.herovole.blogproj.application.user.reportusercomment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.reporting.RealReporting;
import org.herovole.blogproj.domain.comment.reporting.Reporting;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Timestamp;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportUserCommentInput {
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

        public ReportUserCommentInput build() {
            if (iPv4Address == null || userId == null || formContent == null) {
                throw new IllegalStateException(ReportUserCommentInput.class.getSimpleName() + "Invalid building process.");
            }
            formContent.println("comment post (parse 2)");
            IntegerId commentSerialNumber = IntegerId.fromFormContentCommentSerialNumber(formContent);
            if (commentSerialNumber.longMemorySignature() != commentSerialNumberConfirmation) {
                throw new IllegalArgumentException("Discrepancy between form and path commentSerialNumbers.");
            }

            return new ReportUserCommentInput(
                    iPv4Address,
                    userId,
                    commentSerialNumber,
                    CommentText.fromFormContentCommentText(formContent)
            );
        }
    }

    private final IPv4Address iPv4Address;
    private final IntegerPublicUserId userId;

    private final IntegerId commentSerialNumber;
    private final CommentText reportingText;

    Reporting buildReporting() {
        return RealReporting.builder()
                .logId(IntegerId.empty())
                .commentSerialNumber(commentSerialNumber)
                .publicUserId(userId)
                .ip(iPv4Address)
                .reportingText(reportingText)
                .isHandled(GenericSwitch.empty())
                .reportTimestamp(Timestamp.now())
                .build();
    }

}

