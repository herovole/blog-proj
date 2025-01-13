package org.herovole.blogproj.domain.comment.reporting;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;

public interface Reporting {
    static Reporting empty() {
        return new EmptyReporting();
    }

    IntegerId getId();

    IntegerId getCommentSerialNumber();

    IntegerPublicUserId getPublicUserId();

    IPv4Address getIPv4Address();

    CommentText getReportingText();

    boolean isEmpty();

}
