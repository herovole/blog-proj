package org.herovole.blogproj.domain.comment.reporting;

import lombok.Builder;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Timestamp;

@Builder
public class ReportingWithUserBannedUntil implements Reporting {

    private final Reporting reporting;
    private final Timestamp userBannedUntil;
    private final Timestamp ipBannedUntil;

    @Override
    public IntegerId getId() {
        return reporting.getId();
    }

    @Override
    public IntegerId getCommentSerialNumber() {
        return reporting.getCommentSerialNumber();
    }

    @Override
    public IntegerPublicUserId getPublicUserId() {
        return reporting.getPublicUserId();
    }

    @Override
    public IPv4Address getIPv4Address() {
        return reporting.getIPv4Address();
    }

    @Override
    public CommentText getReportingText() {
        return reporting.getReportingText();
    }

    @Override
    public boolean isEmpty() {
        return reporting.isEmpty();
    }
}
