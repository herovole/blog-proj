package org.herovole.blogproj.application.user.verifyorganicity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.user.UniversallyUniqueId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class VerifyOrganicityInput {

    private final IPv4Address iPv4Address;
    private final UniversallyUniqueId uuId;
    private final String verificationToken;

}

