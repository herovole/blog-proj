package org.herovole.blogproj.domain.article;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
        return Arrays.stream(arrayOfArticles).map(Article::toJsonModel).toArray(Article.Json[]::new);
    }

    public Stream<Article> stream() {
        return Stream.of(arrayOfArticles);
    }

}
