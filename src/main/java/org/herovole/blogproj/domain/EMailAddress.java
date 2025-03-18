package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EMailAddress {

    public static EMailAddress fromFormContentEMailAddress(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_EMAIL_ADDRESS);
        return valueOf(child.getValue());
    }

    public static EMailAddress valueOf(String address) {
        if (EmailValidator.getInstance().isValid(address)) {
            return new EMailAddress(address);
        }
        throw new DomainInstanceGenerationException(address);
    }

    public static EMailAddress empty() {
        return valueOf(null);
    }

    private static final String API_KEY_EMAIL_ADDRESS = "email";
    private final String address;

    public boolean isEmpty() {
        return address == null || address.isEmpty();
    }

    public String letterSignature() {
        return this.address;
    }

    public String memorySignature() {
        return this.address;
    }
}
