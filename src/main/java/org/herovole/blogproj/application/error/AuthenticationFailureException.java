package org.herovole.blogproj.application.error;

public class AuthenticationFailureException extends ApplicationProcessException {
    public AuthenticationFailureException(String message) {
        super(message);
    }
}
