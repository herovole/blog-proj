package org.herovole.blogproj.domain.time;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Minute {

    private static final String EMPTY = "--";
    private final Integer mm;

    public static Minute valueOf(Integer number) throws DomainInstanceGenerationException {
        if(number < 0 || 59 < number) {
            throw new DomainInstanceGenerationException();
        }
        return new Minute(number);
    }

    public static Minute valueOf(String number) throws DomainInstanceGenerationException {
        try {
            return EMPTY.equals(number) ? empty() : valueOf(Integer.parseInt(number));
        } catch (NumberFormatException e) {
            throw new DomainInstanceGenerationException();
        }
    }

    public static Minute empty() {
        return new Minute(null);
    }

    public boolean isEmpty() {
        return mm == null;
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : String.format("%02d", mm);
    }

    public Integer memorySignature() {
        return this.isEmpty() ? null : mm;
    }
}
