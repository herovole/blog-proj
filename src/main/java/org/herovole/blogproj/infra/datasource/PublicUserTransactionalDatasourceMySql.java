package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.user.PublicUserTransactionalDatasource;
import org.herovole.blogproj.domain.user.UniversallyUniqueId;
import org.herovole.blogproj.infra.hibernate.TransactionCache;
import org.herovole.blogproj.infra.jpa.entity.EPublicUser;
import org.herovole.blogproj.infra.jpa.repository.EPublicUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicUserTransactionalDatasourceMySql extends PublicUserDatasourceMySql implements PublicUserTransactionalDatasource {

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
    protected PublicUserTransactionalDatasourceMySql(EPublicUserRepository ePublicUserRepository) {
        super(ePublicUserRepository);
    }

    @Override
    public int amountOfCachedTransactions() {
        return cacheInsert.amountOfStackedTransactions() +
                cacheInsert.amountOfStackedTransactions() +
                cacheDelete.amountOfStackedTransactions();
    }

    @Override
    public void flush(AppSession session) {
        cacheInsert.flush(session);
        cacheUpdate.flush(session);
        cacheDelete.flush(session);
    }

    @Override
    public void insert(UniversallyUniqueId uuId) {
        if (uuId.isEmpty()) return;
        EPublicUser entity = EPublicUser.fromUuId(uuId);
        cacheInsert.add(entity);
    }
}
