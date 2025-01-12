package org.herovole.blogproj.domain.article;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Articles {

    public static Articles of(List<Article> articles) {
        return new Articles(articles.toArray(Article[]::new));
    }

    public static Articles of(Article[] articles) {
        return new Articles(articles);
    }

    private final Article[] arrayOfArticles;

    public Article.Json[] toJsonRecord() {
        return Arrays.stream(arrayOfArticles).map(Article::toJsonRecord).toArray(Article.Json[]::new);
    }

}
