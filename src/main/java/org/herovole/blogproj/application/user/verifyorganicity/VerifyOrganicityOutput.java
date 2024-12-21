package org.herovole.blogproj.application.user.verifyorganicity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.user.UniversallyUniqueId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class VerifyOrganicityOutput {

    private final UniversallyUniqueId uuId;
    private final Timestamp timestampBannedUntil;

    public boolean hasPassed() {
        return timestampBannedUntil.isEmpty();
    }

    record Json(
            String timestampBannedUntil
    ) {
    }

    public Json toJsonModel() {
        return new Json(timestampBannedUntil.letterSignatureFrontendDisplay());
    }
}

