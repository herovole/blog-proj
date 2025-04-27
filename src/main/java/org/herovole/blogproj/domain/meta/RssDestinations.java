package org.herovole.blogproj.domain.meta;

import org.herovole.blogproj.domain.article.ArticleTransactionalDatasource;

public interface RssDestinations {

    ArticleTransactionalDatasource getRss20Destination();

    ArticleTransactionalDatasource getRss10Destination();

    ArticleTransactionalDatasource getRssFeedDestination();

    ArticleTransactionalDatasource getGermanRss20Destination();

    ArticleTransactionalDatasource getGermanRss10Destination();
}
