package org.herovole.blogproj.domain.time;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Hour {

    private static final String EMPTY = "--";
    private final Integer hh;

    public static Hour valueOf(Integer number)  {
        if(number < 0 || 23 < number) {
            throw new DomainInstanceGenerationException();
        }
        return new Hour(number);
    }

    public static Hour valueOf(String number)  {
        try {
            return EMPTY.equals(number) ? empty() : valueOf(Integer.parseInt(number));
        } catch (NumberFormatException e) {
            throw new DomainInstanceGenerationException();
        }
    }

    public static Hour empty() {
        return new Hour(null);
    }

    public boolean isEmpty() {
        return hh == null;
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : String.format("%02d", hh);
    }

    public Integer memorySignature() {
        return this.isEmpty() ? null : hh;
    }
}
