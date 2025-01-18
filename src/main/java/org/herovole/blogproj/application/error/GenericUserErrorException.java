package org.herovole.blogproj.application.error;

public class GenericUserErrorException extends ApplicationProcessException {
    public GenericUserErrorException(String message) {
        super(message);
    }
}
