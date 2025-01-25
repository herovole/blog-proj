package org.herovole.blogproj.domain.adminuser;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.time.Timestamp;

@Getter
@Builder
public class RealAdminUserRecognition implements AdminUser {
    private final UserName userName;
    private final Role role;
    private final IPv4Address accessTokenIp;
    private final Timestamp accessTokenExpiry;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String getCredentialEncode() {
        throw new UnsupportedOperationException(RealAdminUserRecognition.class.getSimpleName());
    }

    @Override
    public AccessToken getAccessToken() {
        throw new UnsupportedOperationException(RealAdminUserRecognition.class.getSimpleName());
    }

    @Override
    public AdminUser appendTokenInfo(AccessToken accessToken, IPv4Address accessTokenIp, Timestamp accessTokenExpiry) {
        return builder()
                .userName(this.userName)
                .role(this.role)
                .accessTokenIp(accessTokenIp)
                .accessTokenExpiry(accessTokenExpiry)
                .build();
    }

    @Override
    public boolean isCoherentTo(AdminUser legitimateUserInfo) {
        return this.userName.equals(legitimateUserInfo.getUserName()) &&
                this.role.equals(legitimateUserInfo.getRole()) &&
                this.accessTokenIp.equals(legitimateUserInfo.getAccessTokenIp()) &&

                this.accessTokenExpiry.precedes(legitimateUserInfo.getAccessTokenExpiry());
    }

    @Override
    public Json toJsonModel() {
        throw new UnsupportedOperationException(RealAdminUserRecognition.class.getSimpleName());
    }
}
