package org.herovole.blogproj.domain.publicuser;

import org.herovole.blogproj.application.AppSession;

public interface PublicUserTransactionalDatasource {

    int amountOfCachedTransactions();

    void flush(AppSession session);

    void insert(UniversallyUniqueId uuId);
}
