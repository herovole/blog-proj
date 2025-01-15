package org.herovole.blogproj.application.auth.checkuserban;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CheckUserBanInput {

    private final IPv4Address iPv4Address;
    private final IntegerPublicUserId userId;

}

