package org.herovole.blogproj.domain.user;

import org.herovole.blogproj.domain.time.Timestamp;

public interface PublicUserDatasource {
    IntegerPublicUserId findIdByUuId(UniversallyUniqueId uuId);

    Timestamp isBannedUntil(UniversallyUniqueId uuId);
}
