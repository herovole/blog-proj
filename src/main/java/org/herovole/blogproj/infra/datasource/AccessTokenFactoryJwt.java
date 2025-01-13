package org.herovole.blogproj.infra.datasource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.adminuser.AccessTokenFactory;
import org.herovole.blogproj.domain.adminuser.AdminUser;

import javax.crypto.SecretKey;
import java.util.Date;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessTokenFactoryJwt implements AccessTokenFactory {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String CLAIM_KEY_ROLE = "role";

    public static AccessTokenFactory of(int expirationHours) {
        return new AccessTokenFactoryJwt(expirationHours * 3600 * 1000L);  // Convert hours to milliseconds
    }

    private final long expirationTimeInMilliseconds;

    public AccessToken generateToken(AdminUser adminUser) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expirationTimeInMilliseconds);

        String accessToken = Jwts.builder()
                .setSubject(adminUser.getUserName().memorySignature())            // Set the subject (user identifier)
                .claim(CLAIM_KEY_ROLE, adminUser.getRole().getCode())             // Custom claim: user role
                .setIssuedAt(issuedAt)           // Set the issued date
                .setExpiration(expiration)       // Set the expiration date
                .signWith(SECRET_KEY)            // Sign with the secret key
                .compact();                      // Generate the token
        return AccessToken.valueOf(accessToken);
    }


    public void validateToken(AccessToken accessToken) throws SignatureException {
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

        // Print extracted claims
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);
    }

}

