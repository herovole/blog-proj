package org.herovole.blogproj.domain.adminuser;

import org.herovole.blogproj.domain.EMailAddress;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.time.Timestamp;

public class EmptyAdminUser implements AdminUser {
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public UserName getUserName() {
        return UserName.empty();
    }

    @Override
    public EMailAddress getEMailAddress() {
        return EMailAddress.empty();
    }

    @Override
    public Role getRole() {
        return Role.NONE;
    }

    @Override
    public String getCredentialEncode() {
        return null;
    }

    @Override
    public VerificationCode getVerificationCode() {
        return VerificationCode.empty();
    }

    @Override
    public Timestamp getVerificationCodeExpiry() {
        return Timestamp.empty();
    }

    @Override
    public IPv4Address getAccessTokenIp() {
        return IPv4Address.empty();
    }

    @Override
    public AccessToken getAccessToken() {
        return AccessToken.empty();
    }

    @Override
    public Timestamp getAccessTokenExpiry() {
        return Timestamp.empty();
    }

    @Override
    public AdminUser appendTokenInfo(AccessToken accessToken, IPv4Address accessTokenIp, Timestamp accessTokenExpiry) {
        throw new IllegalStateException("Empty Object");
    }

    @Override
    public AdminUser appendVerificationCodeInfo(VerificationCode verificationCode, Timestamp verificationCodeExpiry) {
        throw new IllegalStateException("Empty Object");
    }

    @Override
    public boolean isCoherentTo(AdminUser user) {
        return false;
    }

    @Override
    public boolean hasCoherentVerificationCode(VerificationCode verificationCode) {
        return false;
    }

    @Override
    public Json toJsonModel() {
        throw new UnsupportedOperationException(EmptyAdminUser.class.getSimpleName());
    }

}
