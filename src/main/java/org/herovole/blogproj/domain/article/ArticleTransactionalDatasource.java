package org.herovole.blogproj.domain.article;

import org.herovole.blogproj.application.AppSession;

public interface ArticleTransactionalDatasource {

    int amountOfCachedTransactions();

    void flush(AppSession session);
    void insert(ArticleEditingPage article);
    void update(ArticleEditingPage before, ArticleEditingPage after);
}
