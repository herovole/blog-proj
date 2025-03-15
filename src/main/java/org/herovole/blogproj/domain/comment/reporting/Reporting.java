package org.herovole.blogproj.domain.comment.reporting;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Timestamp;

public interface Reporting {
    static Reporting empty() {
        return new EmptyReporting();
    }

    IntegerId getId();

    IntegerId getCommentSerialNumber();

    IntegerPublicUserId getPublicUserId();

    IPv4Address getIPv4Address();

    CommentText getReportingText();

    Timestamp getReportTimestamp();

    default long getPostedSecondsAgo() {
        if (this.isEmpty()) return Long.MAX_VALUE;
        return Timestamp.now().minusInSeconds(this.getReportTimestamp());
    }

    boolean isEmpty();

    Json toJsonModel();

    interface Json {
    }

}
