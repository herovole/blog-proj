package org.herovole.blogproj.domain.article;

import org.herovole.blogproj.domain.IntegerId;

public interface ArticleDatasource {
    ArticleEditingPage findById(IntegerId articleId);
}
