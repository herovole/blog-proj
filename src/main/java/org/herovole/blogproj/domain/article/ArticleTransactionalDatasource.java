package org.herovole.blogproj.domain.article;

import org.herovole.blogproj.application.AppSession;

public interface ArticleTransactionalDatasource {

    int amountOfCachedTransactions();

    void flush(AppSession session);
    void insert(Article article);
    void update(Article before, Article after);
}
