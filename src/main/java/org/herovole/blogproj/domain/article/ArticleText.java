package org.herovole.blogproj.domain.article;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleText {
    private static final int LENGTH_LIMIT = 2047;
    private static final String API_KEY_TEXT = "articleText";

    public static ArticleText fromPostContent(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_TEXT);
        return valueOf(URLDecoder.decode(child.getValue(), StandardCharsets.UTF_8));
    }

    public static ArticleText valueOf(String text) {
        if (text == null) return empty();
        if (LENGTH_LIMIT < text.length()) throw new DomainInstanceGenerationException(text);
        return new ArticleText(text);
    }

    public static ArticleText empty() {
        return new ArticleText(null);
    }

    private final String text;

    public String memorySignature() {
        return this.text;
    }
}
