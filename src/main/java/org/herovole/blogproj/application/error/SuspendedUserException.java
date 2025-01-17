package org.herovole.blogproj.application.error;

public class SuspendedUserException extends ApplicationProcessException {
    public SuspendedUserException(String message) {
        super(message);
    }
}
