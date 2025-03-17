package org.herovole.blogproj.domain.adminuser;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.EMailAddress;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.time.Timestamp;

@Getter
@Builder
public class RealAdminUser implements AdminUser {
    private final IntegerId id;
    private final UserName userName;
    private final EMailAddress email;
    private final Role role;
    private final String credentialEncode;
    private final VerificationCode verificationCode;
    private final Timestamp verificationCodeExpiry;
    private final AccessToken accessToken;
    private final IPv4Address accessTokenIp;
    private final Timestamp accessTokenExpiry;
    private final Timestamp timestampLastLogin;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public EMailAddress getEMailAddress() {
        return this.email;
    }

    @Override
    public AdminUser appendTokenInfo(AccessToken accessToken, IPv4Address accessTokenIp, Timestamp accessTokenExpiry) {
        return builder()
                .id(this.id)
                .userName(this.userName)
                .email(this.email)
                .role(this.role)
                .credentialEncode(this.credentialEncode)
                .verificationCode(this.verificationCode)
                .verificationCodeExpiry(this.verificationCodeExpiry)
                .accessToken(accessToken)
                .accessTokenIp(accessTokenIp)
                .accessTokenExpiry(accessTokenExpiry)
                .timestampLastLogin(this.timestampLastLogin)
                .build();
    }

    @Override
    public AdminUser appendVerificationCodeInfo(VerificationCode verificationCode, Timestamp verificationCodeExpiry) {
        return builder()
                .id(this.id)
                .userName(this.userName)
                .email(this.email)
                .role(this.role)
                .credentialEncode(this.credentialEncode)
                .verificationCode(verificationCode)
                .verificationCodeExpiry(verificationCodeExpiry)
                .accessToken(this.accessToken)
                .accessTokenIp(this.accessTokenIp)
                .accessTokenExpiry(this.accessTokenExpiry)
                .timestampLastLogin(this.timestampLastLogin)
                .build();
    }

    @Override
    public boolean isCoherentTo(AdminUser user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasCoherentVerificationCode(VerificationCode verificationCode) {
        return !this.verificationCode.isEmpty() &&
                !verificationCode.isEmpty() &&
                this.verificationCode.equals(verificationCode);
    }

    @Override
    public Json toJsonModel() {
        return new Json(
                id.intMemorySignature(),
                userName.memorySignature(),
                email.letterSignature(),
                role.getCode(),
                timestampLastLogin.letterSignatureFrontendDisplay());
    }
}
