package org.herovole.blogproj.domain.publicuser;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.time.Timestamp;

public interface PublicIpTransactionalDatasource {

    int amountOfCachedTransactions();

    void flush(AppSession session);

    void insert(IPv4Address ip);

    void suspend(IPv4Address ip, Timestamp bannedUntil);
}
