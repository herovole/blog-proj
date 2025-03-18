package org.herovole.blogproj.domain.adminuser;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;

import java.util.Random;
import java.util.regex.Pattern;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class VerificationCode {
    private static final String API_KEY_VERIFICATION_CODE = "verificationCode";
    private static final Pattern pattern999999 = Pattern.compile("\\d{6}");
    private static final Random random = new Random();

    public static VerificationCode fromFormContentVerificationCode(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_VERIFICATION_CODE);
        return valueOf(child.getValue());
    }

    public static VerificationCode generateCode() {
        String num = String.valueOf(random.nextInt(0, 1000000));
        String sixDigits = String.format("%6s", num).replace(' ', '0');
        return VerificationCode.valueOf(sixDigits);
    }

    public static VerificationCode valueOf(String code) {
        if (code == null || code.isEmpty()) return empty();
        if (pattern999999.matcher(code).matches()) {
            return new VerificationCode(code);
        }
        throw new DomainInstanceGenerationException(code);
    }

    public static VerificationCode empty() {
        return new VerificationCode(null);
    }

    private final String code;

    public boolean isEmpty() {
        return code == null || code.isEmpty();
    }

    public String letterSignature() {
        return code;
    }

    public String memorySignature() {
        return code;
    }
}
