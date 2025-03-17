package org.herovole.blogproj.domain.adminuser;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.time.Timestamp;

@Getter
@Builder
public class RealAdminUser implements AdminUser {
    private final IntegerId id;
    private final UserName userName;
    private final Role role;
    private final String credentialEncode;
    private final AccessToken accessToken;
    private final IPv4Address accessTokenIp;
    private final Timestamp accessTokenExpiry;
    private final Timestamp timestampLastLogin;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public AdminUser appendTokenInfo(AccessToken accessToken, IPv4Address accessTokenIp, Timestamp accessTokenExpiry) {
        return builder()
                .id(this.id)
                .userName(this.userName)
                .role(this.role)
                .credentialEncode(this.credentialEncode)
                .accessToken(accessToken)
                .accessTokenIp(accessTokenIp)
                .accessTokenExpiry(accessTokenExpiry)
                .timestampLastLogin(this.timestampLastLogin)
                .build();
    }

    @Override
    public boolean isCoherentTo(AdminUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Json toJsonModel() {
        return new Json(
                id.intMemorySignature(),
                userName.memorySignature(),
                role.getCode(),
                timestampLastLogin.letterSignatureFrontendDisplay());
    }
}
