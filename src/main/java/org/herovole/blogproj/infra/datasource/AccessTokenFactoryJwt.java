package org.herovole.blogproj.infra.datasource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.adminuser.AccessTokenFactory;
import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.herovole.blogproj.domain.adminuser.RealAdminUserClaim;
import org.herovole.blogproj.domain.adminuser.Role;
import org.herovole.blogproj.domain.adminuser.UserName;
import org.herovole.blogproj.domain.time.Timestamp;

import javax.crypto.SecretKey;
import java.util.Date;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessTokenFactoryJwt implements AccessTokenFactory {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String CLAIM_KEY_ROLE = "role";
    private static final String CLAIM_KEY_IP = "ip";

    public static AccessTokenFactory of(int expirationHours) {
        return new AccessTokenFactoryJwt(expirationHours);  // Convert hours to milliseconds
    }

    private final int expirationTimeInHours;

    @Override
    public Timestamp getExpectedExpirationTime() {
        return Timestamp.now().shiftHours(expirationTimeInHours);
    }

    @Override
    public AccessToken generateToken(AdminUser adminUser) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expirationTimeInHours * 3600 * 1000L);

        String accessToken = Jwts.builder()
                .setSubject(adminUser.getUserName().memorySignature())            // Set the subject (user identifier)
                .claim(CLAIM_KEY_ROLE, adminUser.getRole().getCode())             // Custom claim: user role
                .claim(CLAIM_KEY_IP, adminUser.getAccessTokenIp().aton())
                .setIssuedAt(issuedAt)           // Set the issued date
                .setExpiration(expiration)       // Set the expiration date
                .signWith(SECRET_KEY)            // Sign with the secret key
                .compact();                      // Generate the token
        return AccessToken.valueOf(accessToken);
    }


    @Override
    public AdminUser validateToken(AccessToken accessToken) throws SignatureException {
        if (accessToken.isEmpty()) throw new IllegalArgumentException("Access token must not be empty");
        // Parse the token and validate it
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Set the signing key
                .build()
                .parseClaimsJws(accessToken.memorySignature());   // Parse and validate

        // Extract claims
        Claims claims = claimsJws.getBody();
        String username = claims.getSubject();
        String role = claims.get(CLAIM_KEY_ROLE, String.class);
        long ip = claims.get(CLAIM_KEY_IP, Long.class);


        return RealAdminUserClaim.builder()
                .userName(UserName.valueOf(username))
                .role(Role.valueOf(role))
                .accessToken(accessToken)
                .accessTokenIp(IPv4Address.valueOf(ip))
                .accessTokenExpiry(Timestamp.valueOf(claims.getExpiration()))
                .build();
    }

}

