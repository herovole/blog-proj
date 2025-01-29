package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.publicuser.PublicIpTransactionalDatasource;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.infra.hibernate.TransactionCache;
import org.herovole.blogproj.infra.jpa.entity.EPublicIp;
import org.herovole.blogproj.infra.jpa.repository.EPublicIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicIpTransactionalDatasourceMySql extends PublicIpDatasourceMySql implements PublicIpTransactionalDatasource {

    private static final TransactionCache<Object> cacheInsert = new TransactionCache<>() {
        @Override
        protected void doTransaction(Object transaction, AppSession session) {
            session.insert(transaction);
        }
    };
    private static final TransactionCache<Object> cacheUpdate = new TransactionCache<>() {
        @Override
        protected void doTransaction(Object transaction, AppSession session) {
            session.update(transaction);
        }
    };
    private static final TransactionCache<String> cacheDelete = new TransactionCache<>() {
        @Override
        protected void doTransaction(String transaction, AppSession session) {
            session.executeSql(transaction);
        }
    };

    @Autowired
    public PublicIpTransactionalDatasourceMySql(EPublicIpRepository ePublicIpRepository) {
        super(ePublicIpRepository);
    }

    @Override
    public int amountOfCachedTransactions() {
        return cacheInsert.amountOfStackedTransactions() +
                cacheUpdate.amountOfStackedTransactions() +
                cacheDelete.amountOfStackedTransactions();
    }

    @Override
    public void flush(AppSession session) {
        cacheInsert.flush(session);
        cacheUpdate.flush(session);
        cacheDelete.flush(session);
    }

    @Override
    public void insert(IPv4Address ip) {
        EPublicIp entity = new EPublicIp();
        entity.setAton(ip.aton());
        cacheInsert.add(entity);
    }

    @Override
    public void suspend(IPv4Address ip, Timestamp bannedUntil) {
        EPublicIp entity = this.ePublicIpRepository.findByAton(ip.aton());
        entity.setBannedUntil(bannedUntil.toLocalDateTime());
        cacheUpdate.add(entity);
    }
}
