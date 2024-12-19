package org.herovole.blogproj.domain.user;

import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.time.Timestamp;

public interface PublicUserDatasource {
    IntegerId findIdByUuId(UniversallyUniqueId uuId);

    Timestamp isBannedUntil(UniversallyUniqueId uuId);
}
