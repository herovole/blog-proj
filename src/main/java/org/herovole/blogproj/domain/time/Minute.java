package org.herovole.blogproj.domain.time;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Minute {

    private static final String API_KEY_REGISTRATION_MINUTE = "registrationMinute";
    private static final String EMPTY = "--";
    private final Integer mm;

    public static Minute fromFormContentRegistrationMinute(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_REGISTRATION_MINUTE);
        return valueOf(child.getValue());
    }

    public static Minute valueOf(Integer number)  {
        if(number < 0 || 59 < number) {
            throw new DomainInstanceGenerationException(number);
        }
        return new Minute(number);
    }

    public static Minute valueOf(String number)  {
        try {
            return EMPTY.equals(number) ? empty() : valueOf(Integer.parseInt(number));
        } catch (NumberFormatException e) {
            throw new DomainInstanceGenerationException(number);
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
