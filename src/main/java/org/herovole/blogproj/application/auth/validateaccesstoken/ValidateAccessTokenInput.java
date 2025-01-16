package org.herovole.blogproj.application.auth.validateaccesstoken;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.adminuser.InitialAdminRequest;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateAccessTokenInput {

    private final IntegerPublicUserId userId;
    private final IPv4Address ip;
    private final AccessToken accessToken;
}
