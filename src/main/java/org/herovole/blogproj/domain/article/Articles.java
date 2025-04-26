package org.herovole.blogproj.domain.article;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Articles {

    public static Articles of(List<Article> articles) {
        return new Articles(articles.toArray(Article[]::new));
    }

    public static Articles of(Article[] articles) {
        return new Articles(articles);
    }

    public static Articles empty() {
        return new Articles(new Article[0]);
    }

    private final Article[] arrayOfArticles;

    public Articles append(Articles that) {

        Article[] merged = Stream.concat(this.stream(), that.stream())
                .collect(Collectors.toMap(
                        Article::getArticleId,        // use ID as key
                        a -> a,          // value is the instance
                        (a1, a2) -> a1 // adopt this article if duplicated.
                ))
                .values().stream()
                .sorted((a1, a2) ->
                        a2.getRegistrationTimestamp().compareTo(a1.getRegistrationTimestamp())) // descending
                .toArray(Article[]::new);
        return Articles.of(merged);
    }

    public Articles sliceFirst(int number) {
        Article[] articles = Arrays.copyOf(this.arrayOfArticles, Math.min(number, this.arrayOfArticles.length));
        return Articles.of(articles);
    }

    public Article.Json[] toJsonRecord() {
        return Arrays.stream(arrayOfArticles).map(Article::toJsonModel).toArray(Article.Json[]::new);
    }

    public Stream<Article> stream() {
        return Stream.of(arrayOfArticles);
    }

}
