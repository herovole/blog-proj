package org.herovole.blogproj.domain.source;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.validator.routines.UrlValidator;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SourceUrl {

    private static final int LENGTH_LIMIT = 200;
    private static final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    private static final String LOCAL_TESTING_URL = "https://localhost:8080";
    private static final String API_KEY_SOURCE_URL = "sourceUrl";

    public static SourceUrl fromPostContent(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_SOURCE_URL);
        return valueOf(URLDecoder.decode(child.getValue(), StandardCharsets.UTF_8));
    }

    public static SourceUrl valueOf(String url) {
        if (url == null || url.isEmpty() || url.isBlank()) return empty();
        if (url.startsWith(LOCAL_TESTING_URL)) return new SourceUrl(url);
        if (LENGTH_LIMIT < url.length()) throw new DomainInstanceGenerationException(url);
        if (!urlValidator.isValid(url)) throw new DomainInstanceGenerationException(url);
        return new SourceUrl(url);
    }

    public static SourceUrl empty() {
        return new SourceUrl(null);
    }

    private final String url;

    public String memorySignature() {
        return this.url;
    }
}
