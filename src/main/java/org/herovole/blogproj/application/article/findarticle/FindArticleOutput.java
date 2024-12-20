package org.herovole.blogproj.application.article.findarticle;

import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.article.Article;

@RequiredArgsConstructor
public class FindArticleOutput {
    private final Article article;

    public Json toJsonRecord() {
        return new Json(article.toJsonRecord());
    }

    record Json(Article.Json article) {
    }
}
