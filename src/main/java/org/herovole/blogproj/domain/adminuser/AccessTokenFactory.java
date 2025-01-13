package org.herovole.blogproj.domain.adminuser;

import io.jsonwebtoken.security.SignatureException;

public interface AccessTokenFactory {
    AccessToken generateToken(AdminUser adminUser);

    void validateToken(AccessToken accessToken) throws SignatureException;
}
