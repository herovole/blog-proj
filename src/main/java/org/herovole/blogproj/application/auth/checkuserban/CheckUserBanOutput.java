package org.herovole.blogproj.application.auth.checkuserban;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CheckUserBanOutput {

    private final IntegerPublicUserId userId;
    private final Timestamp timestampBannedUntil;

    public boolean hasPassed() {
        return timestampBannedUntil.isEmpty();
    }

}

