package org.herovole.blogproj.domain.source;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.PostContent;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.ValidationException;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SourceUrl {

    private static final int LENGTH_LIMIT = 200;
    //private static final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    private static final String API_KEY_SOURCE_URL = "sourceUrl";

    public static SourceUrl fromPostContent(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_SOURCE_URL);
        return valueOf(child.getValue());
    }

    public static SourceUrl valueOf(String url) {
        if (url == null) return empty();
        try {
            String safeUrl = ESAPI.validator().getValidInput("URL", url, "HTTPURL", LENGTH_LIMIT, false);
            return new SourceUrl(safeUrl);
        } catch (ValidationException e) {
            System.out.println(e);
            throw new DomainInstanceGenerationException();
        }
    }

    public static SourceUrl empty() {
        return new SourceUrl(null);
    }

    private final String url;

    public String memorySignature() {
        return this.url;
    }
}
