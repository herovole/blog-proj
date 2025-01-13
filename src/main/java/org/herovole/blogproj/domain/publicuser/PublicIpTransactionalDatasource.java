package org.herovole.blogproj.domain.publicuser;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.IPv4Address;

public interface PublicIpTransactionalDatasource {

    int amountOfCachedTransactions();

    void flush(AppSession session);

    void insert(IPv4Address ip);
}
