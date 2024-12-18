package org.herovole.blogproj.domain.comment;

import org.herovole.blogproj.application.AppSession;

public interface UserCommentTransactionalDatasource {

    int amountOfCachedTransactions();

    void flush(AppSession session);

    void insert(CommentUnit commentUnit);
}
