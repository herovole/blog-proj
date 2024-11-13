package org.herovole.blogproj.domain.tag;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CountryCode implements Comparable<CountryCode> {

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

    @Override
    public int compareTo(CountryCode o) {
        return this.code.compareTo(o.code);
    }
}
