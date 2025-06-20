package org.herovole.blogproj.domain.adminuser;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.EMailAddress;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.time.Timestamp;

@Getter
@Builder
public class RealAdminUserClaim implements AdminUser {
    private final UserName userName;
    private final Role role;
    private final AccessToken accessToken;
    private final IPv4Address accessTokenIp;
    private final Timestamp accessTokenExpiry;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public EMailAddress getEMailAddress() {
        throw new UnsupportedOperationException(RealAdminUserClaim.class.getSimpleName());
    }

    @Override
    public String getCredentialEncode() {
        throw new UnsupportedOperationException(RealAdminUserClaim.class.getSimpleName());
    }

    @Override
    public VerificationCode getVerificationCode() {
        throw new UnsupportedOperationException(RealAdminUserClaim.class.getSimpleName());
    }

    @Override
    public Timestamp getVerificationCodeExpiry() {
        throw new UnsupportedOperationException(RealAdminUserClaim.class.getSimpleName());
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

    @Override
    public AdminUser appendVerificationCodeInfo(VerificationCode verificationCode, Timestamp verificationCodeExpiry) {
        throw new UnsupportedOperationException(RealAdminUserClaim.class.getSimpleName());
    }

    @Override
    public boolean isCoherentTo(AdminUser legitimateUserInfo) {
        return this.userName.equals(legitimateUserInfo.getUserName()) &&
                this.role.equals(legitimateUserInfo.getRole()) &&
                this.accessTokenIp.equals(legitimateUserInfo.getAccessTokenIp()) &&
                this.accessToken.equals(legitimateUserInfo.getAccessToken()) &&

                this.accessTokenExpiry.precedesOrEquals(legitimateUserInfo.getAccessTokenExpiry());
    }

    @Override
    public boolean hasCoherentVerificationCode(VerificationCode verificationCode) {
        return false;
    }

    @Override
    public Json toJsonModel() {
        throw new UnsupportedOperationException(RealAdminUserClaim.class.getSimpleName());
    }
}
