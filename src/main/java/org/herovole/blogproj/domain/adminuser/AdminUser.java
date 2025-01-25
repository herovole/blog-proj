package org.herovole.blogproj.domain.adminuser;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.time.Timestamp;

public interface AdminUser {
    static AdminUser empty() {
        return new EmptyAdminUser();
    }

    boolean isEmpty();

    UserName getUserName();

    Role getRole();

    String getCredentialEncode();

    IPv4Address getAccessTokenIp();

    AccessToken getAccessToken();

    Timestamp getAccessTokenExpiry();

    AdminUser appendTokenInfo(AccessToken accessToken, IPv4Address accessTokenIp, Timestamp accessTokenExpiry);

    boolean isCoherentTo(AdminUser user);

    Json toJsonModel();
    record Json(int id, String name, String role, String timestampLastLogin) {
    }
}
