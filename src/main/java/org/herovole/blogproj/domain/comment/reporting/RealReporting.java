package org.herovole.blogproj.domain.comment.reporting;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Timestamp;

@Builder
@Getter
public class RealReporting implements Reporting {
    private final IntegerId logId;
    private final IntegerId commentSerialNumber;
    private final IntegerPublicUserId publicUserId;
    private final IPv4Address ip;
    private final CommentText reportingText;
    private final GenericSwitch isHandled;
    private final Timestamp reportTimestamp;

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

    @Builder
    static class Json implements Reporting.Json {
        private int logId;
        private long commentSerialNumber;
        private long userId;
        private String ip;
        private String text;
        private boolean isHandled;
        private String reportTimestamp;
    }

    @Override
    public Json toJsonModel() {
        return Json.builder()
                .logId(this.logId.intMemorySignature())
                .commentSerialNumber(this.commentSerialNumber.longMemorySignature())
                .userId(this.publicUserId.longMemorySignature())
                .ip(this.ip.toRegularFormat())
                .text(this.reportingText.memorySignature())
                .isHandled(this.isHandled.isTrue())
                .reportTimestamp(this.reportTimestamp.letterSignatureFrontendDisplay())
                .build();
    }

}
