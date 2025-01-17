package org.herovole.blogproj.application.error;

public abstract class ApplicationProcessException extends Exception {
    ApplicationProcessException(String message) {
        super(message);
    }
}
