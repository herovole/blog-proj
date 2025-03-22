package org.herovole.blogproj.application.auth.trackuser;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TrackUserInput {

    private final UniversallyUniqueId uuId;

}

