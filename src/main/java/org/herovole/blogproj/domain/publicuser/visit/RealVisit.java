package org.herovole.blogproj.domain.publicuser.visit;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Timestamp;

@Builder
@Getter
public class RealVisit implements Visit {
    private final IntegerId id;
    private final IntegerId articleId;
    private final IntegerPublicUserId userId;
    private final IPv4Address ip;
    private final Timestamp accessTimestamp;

    @Override
    public boolean isEmpty() {
        return false;
    }
}
