package org.herovole.blogproj.domain.tag.country;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.FormContents;

import java.util.Arrays;
import java.util.stream.Stream;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CountryCodes {

    private static final String API_KEY_COUNTRY = "countries";
    private static final String SEP = ",";

    public static CountryCodes fromPostContent(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_COUNTRY);
        FormContents arrayChildren = child.getInArray();
        CountryCode[] codes = arrayChildren.stream().map(p -> CountryCode.valueOf(p.getValue())).filter(e -> !e.isEmpty()).toArray(CountryCode[]::new);
        return of(codes);
    }

    public static CountryCodes fromFormContentInCommaSeparatedString(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_COUNTRY);
        String[] rawCodes = child.getValue().split(SEP);
        CountryCode[] codes = Arrays.stream(rawCodes).map(CountryCode::valueOf).filter(e -> !e.isEmpty()).toArray(CountryCode[]::new);
        return of(codes);
    }

    public static CountryCodes of(CountryCode[] codes) {
        return new CountryCodes(codes);
    }

    public static CountryCodes empty() {
        return new CountryCodes(null);
    }

    private final CountryCode[] codes;

    public Stream<CountryCode> stream() {
        return Arrays.stream(this.codes);
    }

    public boolean has(CountryCode countryCode) {
        return Arrays.asList(codes).contains(countryCode);
    }

    public CountryCode get(int index) {
        if (index < this.codes.length) {
            return this.codes[index];
        }
        return CountryCode.empty();
    }

    public int size() {
        return this.codes.length;
    }

    /**
     * @param that another CountryCodes
     * @return CountryCodes that has, but this doesn't.
     */
    public CountryCodes lack(CountryCodes that) {
        return CountryCodes.of(that.stream().filter(e -> !this.has(e)).toArray(CountryCode[]::new));
    }

    public CountryCode getOne() {
        return codes.length == 0 ? CountryCode.empty() : codes[0];
    }

    public String[] toMemorySignature() {
        return stream().map(CountryCode::memorySignature).toArray(String[]::new);
    }

}
