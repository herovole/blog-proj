package org.herovole.blogproj.application.article.searcharticles;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.article.Article;
import org.herovole.blogproj.domain.article.Articles;

@Builder
@Getter
public class SearchArticlesOutput {
    private final Articles articles;
    private final long totalArticles;

    public Json toJsonModel() {
        return new Json(articles.toJsonRecord(), totalArticles);
    }

    public record Json(Article.Json[] articles,
                long totalArticles) {
    }
}
