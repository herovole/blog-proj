package org.herovole.blogproj.application.user.postusercomment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessPostUserCommentOutput {

    private final UniversallyUniqueId uuId;

    private final Timestamp timestampBannedUntil;
    private final Boolean isHuman;
    private final Boolean hasValidContent;
    private final Boolean isSuccessful;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public Json toJsonModel() {
        return new Json(
                timestampBannedUntil.isEmpty(),
                timestampBannedUntil.letterSignatureFrontendDisplay(),
                isHuman,
                hasValidContent,
                isSuccessful);
    }

    record Json(
            Boolean isNotBanned,
            String timestampBannedUntil,
            Boolean isHuman,
            Boolean hasValidContent,
            Boolean isSuccessful
    ) {
    }
}

