package org.herovole.blogproj.application.auth.trackuser;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;
import org.herovole.blogproj.domain.time.Timestamp;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TrackUserOutput {

    private final IntegerPublicUserId userId;
    private final UniversallyUniqueId uuId;

}

