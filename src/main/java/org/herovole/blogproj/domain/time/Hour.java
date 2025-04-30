package org.herovole.blogproj.domain.time;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Hour {

    private static final String API_KEY_REGISTRATION_HOUR = "registrationHour";
    private static final String EMPTY = "--";
    private final Integer hh;

    public static Hour fromFormContentRegistrationHour(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_REGISTRATION_HOUR);
        return valueOf(child.getValue());
    }

    public static Hour valueOf(Integer number)  {
        if(number < 0 || 23 < number) {
            throw new DomainInstanceGenerationException(number);
        }
        return new Hour(number);
    }

    public static Hour valueOf(String number)  {
        try {
            return EMPTY.equals(number) ? empty() : valueOf(Integer.parseInt(number));
        } catch (NumberFormatException e) {
            throw new DomainInstanceGenerationException(number);
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
