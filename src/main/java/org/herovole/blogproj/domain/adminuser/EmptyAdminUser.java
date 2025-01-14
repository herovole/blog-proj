package org.herovole.blogproj.domain.adminuser;

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
    public Role getRole() {
        return Role.NONE;
    }

    @Override
    public String getCredentialEncode() {
        return null;
    }

    @Override
    public AdminUser appendTokenInfo(AccessToken accessToken, IPv4Address accessTokenIp, Timestamp accessTokenExpiry) {
        throw new IllegalStateException("Empty Object");
    }

}
