package org.herovole.blogproj.domain.publicuser;

import org.herovole.blogproj.domain.time.Timestamp;

public interface PublicUserDatasource {
    IntegerPublicUserId findIdByUuId(UniversallyUniqueId uuId);

    boolean exists(IntegerPublicUserId userId);
    Timestamp isBannedUntil(IntegerPublicUserId userId);
}
