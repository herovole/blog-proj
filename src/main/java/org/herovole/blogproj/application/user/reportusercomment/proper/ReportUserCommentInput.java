package org.herovole.blogproj.application.user.reportusercomment.proper;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.reporting.RealReporting;
import org.herovole.blogproj.domain.comment.reporting.Reporting;
import org.herovole.blogproj.domain.user.IntegerPublicUserId;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportUserCommentInput {
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
                .build();
    }

}

