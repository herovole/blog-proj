package org.herovole.blogproj.application.error;

public class AuthorityInsufficientException extends ApplicationProcessException {
    public AuthorityInsufficientException(String message) {
        super(message);
    }
}
