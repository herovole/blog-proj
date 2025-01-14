package org.herovole.blogproj.domain.adminuser;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.time.Timestamp;

@Getter
@Builder
public class RealAdminUser implements AdminUser {
    private final UserName userName;
    private final Role role;
    private final String credentialEncode;
    private final AccessToken accessToken;
    private final IPv4Address accessTokenIp;
    private final Timestamp accessTokenExpiry;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public AdminUser appendTokenInfo(AccessToken accessToken, IPv4Address accessTokenIp, Timestamp accessTokenExpiry) {
        return builder()
                .userName(this.userName)
                .role(this.role)
                .accessToken(accessToken)
                .accessTokenIp(accessTokenIp)
                .accessTokenExpiry(accessTokenExpiry)
                .build();
    }
}
