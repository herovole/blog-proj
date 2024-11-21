package org.herovole.blogproj.domain.tag;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.PostContents;

import java.util.Arrays;
import java.util.stream.Stream;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CountryCodes {

    private static final String API_KEY_COUNTRY = "countries";

    public static CountryCodes fromPostContent(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_COUNTRY);
        PostContents arrayChildren = child.getInArray();
        CountryCode[] codes = arrayChildren.stream().map(p -> CountryCode.valueOf(p.getValue())).toArray(CountryCode[]::new);
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

    /**
     * @param that another CountryCodes
     * @return CountryCodes that has, but this doesn't.
     */
    public CountryCodes lack(CountryCodes that) {
        return CountryCodes.of(that.stream().filter(e -> !this.has(e)).toArray(CountryCode[]::new));
    }

}
