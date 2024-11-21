package org.herovole.blogproj.domain.article;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.PostContent;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleText {
    private static final int LENGTH_LIMIT = 2047;
    private static final String API_KEY_TEXT = "articleText";

    public static ArticleText fromPostContent(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_TEXT);
        return valueOf(child.getValue());
    }

    public static ArticleText valueOf(String text) {
        if (LENGTH_LIMIT < text.length()) throw new DomainInstanceGenerationException();
        return new ArticleText(text);
    }

    private final String text;

    public String memorySignature() {
        return this.text;
    }
}
