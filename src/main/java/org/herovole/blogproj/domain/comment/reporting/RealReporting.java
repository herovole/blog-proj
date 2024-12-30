package org.herovole.blogproj.domain.comment.reporting;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.user.IntegerPublicUserId;

@Builder
@Getter
public class RealReporting implements Reporting {
    private final IntegerId logId;
    private final IntegerId commentSerialNumber;
    private final IntegerPublicUserId publicUserId;
    private final IPv4Address ip;
    private final CommentText reportingText;

    @Override
    public IntegerId getId() {
        return this.logId;
    }

    @Override
    public IPv4Address getIPv4Address() {
        return this.getIp();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}
