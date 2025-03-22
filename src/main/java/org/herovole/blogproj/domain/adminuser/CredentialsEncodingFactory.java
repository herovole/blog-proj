package org.herovole.blogproj.domain.adminuser;

public interface CredentialsEncodingFactory {
    String encodePassword(Password rawPassword);

    boolean matches(Password rawPassword, String encodedPassword);
}
