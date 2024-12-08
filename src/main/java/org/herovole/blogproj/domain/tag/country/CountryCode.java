package org.herovole.blogproj.domain.tag.country;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CountryCode implements Comparable<CountryCode> {

    private static final String API_KEY_COUNTRY = "country";

    public static CountryCode fromPostContent(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_COUNTRY);
        return valueOf(child.getValue());
    }

    public static CountryCode valueOf(String code) {
        if (code == null) return empty();
        if (code.length() != 2) throw new DomainInstanceGenerationException();
        return new CountryCode(code);
    }

    public static CountryCode empty() {
        return new CountryCode(null);
    }

    private final String code;

    public boolean isEmpty() {
        return code == null || code.isEmpty();
    }

    public String memorySignature() {
        return this.code;
    }

    public String letterSignature() {
        return this.code;
    }

    @Override
    public int compareTo(CountryCode o) {
        return this.code.compareTo(o.code);
    }
}
