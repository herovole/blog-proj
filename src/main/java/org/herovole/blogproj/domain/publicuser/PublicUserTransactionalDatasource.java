package org.herovole.blogproj.domain.publicuser;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.time.Timestamp;

public interface PublicUserTransactionalDatasource {

    int amountOfCachedTransactions();

    void flush(AppSession session);

    void insert(UniversallyUniqueId uuId);

    void suspend(IntegerPublicUserId userId, Timestamp banUntil);
}
