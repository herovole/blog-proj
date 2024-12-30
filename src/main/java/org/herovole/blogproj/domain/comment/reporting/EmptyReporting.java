package org.herovole.blogproj.domain.comment.reporting;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.user.IntegerPublicUserId;

public class EmptyReporting implements Reporting {
    @Override
    public IntegerId getId() {
        return IntegerId.empty();
    }

    @Override
    public IntegerId getCommentSerialNumber() {
        return IntegerId.empty();
    }

    @Override
    public IntegerPublicUserId getPublicUserId() {
        return IntegerPublicUserId.empty();
    }

    @Override
    public IPv4Address getIPv4Address() {
        return IPv4Address.empty();
    }

    @Override
    public CommentText getReportingText() {
        return CommentText.empty();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
