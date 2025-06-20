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
    private static final String EMPTY = "--";

    public static CountryCode fromFormContent(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_COUNTRY);
        return valueOf(child.getValue());
    }

    public static CountryCode valueOf(String code) {
        if (code == null || code.isEmpty() || code.equals(EMPTY)) return empty();
        if (code.length() != 2) throw new DomainInstanceGenerationException(code);
        return new CountryCode(code);
    }

    public static CountryCode empty() {
        return new CountryCode(null);
    }

    private final String code;

    public boolean isEmpty() {
        return code == null || code.isEmpty() || code.equals(EMPTY);
    }

    public String memorySignature() {
        return this.isEmpty() ? EMPTY : this.code; // "--" is defined to be the null expression of Country Code
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : this.code;
    }

    @Override
    public int compareTo(CountryCode o) {
        return this.code.compareTo(o.code);
    }
}
