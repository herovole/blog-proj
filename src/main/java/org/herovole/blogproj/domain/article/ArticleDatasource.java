package org.herovole.blogproj.domain.article;

import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;

public interface ArticleDatasource {
    Article findById(IntegerId articleId);

    Article findByIdSimplified(IntegerId articleId);

    IntegerIds searchByOptions(ArticleListSearchOption searchOption);

    long countByOptions(ArticleListSearchOption searchOption);
}
