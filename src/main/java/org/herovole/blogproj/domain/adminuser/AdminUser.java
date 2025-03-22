package org.herovole.blogproj.domain.adminuser;

import org.herovole.blogproj.domain.EMailAddress;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.time.Timestamp;

public interface AdminUser {
    static AdminUser empty() {
        return new EmptyAdminUser();
    }

    boolean isEmpty();

    UserName getUserName();

    EMailAddress getEMailAddress();

    Role getRole();

    String getCredentialEncode();

    VerificationCode getVerificationCode();

    Timestamp getVerificationCodeExpiry();

    IPv4Address getAccessTokenIp();

    AccessToken getAccessToken();

    Timestamp getAccessTokenExpiry();

    AdminUser appendTokenInfo(AccessToken accessToken, IPv4Address accessTokenIp, Timestamp accessTokenExpiry);

    AdminUser appendVerificationCodeInfo(VerificationCode verificationCode, Timestamp verificationCodeExpiry);

    boolean isCoherentTo(AdminUser user);

    boolean hasCoherentVerificationCode(VerificationCode verificationCode);

    Json toJsonModel();

    record Json(int id, String name, String email, String role, String timestampLastLogin) {
    }
}
