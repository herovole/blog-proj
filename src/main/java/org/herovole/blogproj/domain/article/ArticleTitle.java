package org.herovole.blogproj.domain.article;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleTitle {

    private static final int LENGTH_LIMIT = 127;
    private static final String API_KEY_TITLE = "articleTitle";
    private static final String API_KEY_SOURCE_TITLE = "sourceTitle";

    public static ArticleTitle fromPostContentArticleTitle(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_TITLE);
        return valueOf(child.getValue());
    }

    public static ArticleTitle fromPostContentSourceTitle(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_SOURCE_TITLE);
        return valueOf(child.getValue());
    }

    public static ArticleTitle valueOf(String title) {
        if (title == null) return empty();
        if (LENGTH_LIMIT < title.length()) throw new DomainInstanceGenerationException();
        return new ArticleTitle(title);
    }

    public static ArticleTitle empty() {
        return new ArticleTitle(null);
    }

    private final String title;

    public String memorySignature() {
        return this.title;
    }
}
