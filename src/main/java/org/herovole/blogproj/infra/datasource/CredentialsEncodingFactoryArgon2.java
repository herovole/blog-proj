package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.adminuser.CredentialsEncodingFactory;
import org.herovole.blogproj.domain.adminuser.Password;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CredentialsEncodingFactoryArgon2 implements CredentialsEncodingFactory {
    private static final Argon2PasswordEncoder ARGON2 = new Argon2PasswordEncoder(16, 64, 1, 64000, 4);

    public String encodePassword(Password rawPassword) {
        if (rawPassword.isEmpty()) throw new IllegalArgumentException("empty password");
        return ARGON2.encode(rawPassword.memorySignature());
    }

    public boolean matches(Password rawPassword, String encodedPassword) {
        if (rawPassword.isEmpty() || encodedPassword.isEmpty()) throw new IllegalArgumentException("lacks required attributes");
        return ARGON2.matches(rawPassword.memorySignature(), encodedPassword);
    }
}
