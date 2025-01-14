package org.herovole.blogproj.domain.adminuser;

import io.jsonwebtoken.security.SignatureException;
import org.herovole.blogproj.domain.time.Timestamp;

public interface AccessTokenFactory {

    Timestamp getExpectedExpirationTime();
    AccessToken generateToken(AdminUser adminUser);

    void validateToken(AccessToken accessToken) throws SignatureException;
}
