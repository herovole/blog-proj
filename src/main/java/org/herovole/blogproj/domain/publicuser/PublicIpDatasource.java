package org.herovole.blogproj.domain.publicuser;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.time.Timestamp;

public interface PublicIpDatasource {
    boolean isRecorded(IPv4Address ip);

    Timestamp isBannedUntil(IPv4Address ip);
}
