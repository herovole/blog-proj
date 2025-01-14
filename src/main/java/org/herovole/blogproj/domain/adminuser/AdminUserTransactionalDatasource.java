package org.herovole.blogproj.domain.adminuser;

import org.herovole.blogproj.application.AppSession;

public interface AdminUserTransactionalDatasource {
    void update(AdminUser before, AdminUser after);

    int amountOfCachedTransactions();

    void flush(AppSession session);
}
