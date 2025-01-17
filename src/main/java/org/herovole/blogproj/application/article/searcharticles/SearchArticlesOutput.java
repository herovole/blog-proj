package org.herovole.blogproj.application.article.searcharticles;

import lombok.Builder;
import org.herovole.blogproj.domain.article.Article;
import org.herovole.blogproj.domain.article.Articles;

@Builder
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
