package org.herovole.blogproj.domain.article;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.PostContent;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleTitle {

    private static final int LENGTH_LIMIT = 127;
    private static final String API_KEY_TITLE = "articleTitle";
    private static final String API_KEY_SOURCE_TITLE = "sourceTitle";

    public static ArticleTitle fromPostContentArticleTitle(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_TITLE);
        return valueOf(child.getValue());
    }

    public static ArticleTitle fromPostContentSourceTitle(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_SOURCE_TITLE);
        return valueOf(child.getValue());
    }

    public static ArticleTitle valueOf(String title) {
        if (LENGTH_LIMIT < title.length()) throw new DomainInstanceGenerationException();
        return new ArticleTitle(title);
    }

    private final String title;
}
