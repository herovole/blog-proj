package org.herovole.blogproj.domain;

public class DomainInstanceGenerationException extends RuntimeException {
    public DomainInstanceGenerationException() {
        super();
    }

    public DomainInstanceGenerationException(String message) {
        super(message);
    }

    public DomainInstanceGenerationException(Long field) {
        super(Long.toString(field));
    }

    public DomainInstanceGenerationException(Integer field) {
        super(Integer.toString(field));
    }
}
