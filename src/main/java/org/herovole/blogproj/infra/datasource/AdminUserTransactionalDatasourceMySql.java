package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.herovole.blogproj.domain.adminuser.AdminUserTransactionalDatasource;
import org.herovole.blogproj.infra.hibernate.TransactionCache;
import org.herovole.blogproj.infra.jpa.entity.MAdminUser;
import org.herovole.blogproj.infra.jpa.repository.MAdminUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminUserTransactionalDatasourceMySql extends AdminUserDatasourceMySql implements AdminUserTransactionalDatasource {
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

    public AdminUserTransactionalDatasourceMySql(MAdminUserRepository mAdminUserRepository) {
        super(mAdminUserRepository);
    }

    @Override
    public void insert(AdminUser user) {
        if (user.isEmpty()) throw new IllegalArgumentException("Empty AdminUser");
        Integer maxId = mAdminUserRepository.findMaxId();
        MAdminUser newEntity = MAdminUser.fromInsertDomainObj(user);
        newEntity.setId(maxId == null ? 1 : maxId + 1);
        cacheInsert.add(newEntity);
    }

    @Override
    public void update(AdminUser before, AdminUser after) {
        if (before.isEmpty() || after.isEmpty()) throw new IllegalArgumentException("Empty AdminUser");
        List<MAdminUser> users = this.mAdminUserRepository.findByUserName(before.getUserName().memorySignature());
        if (users.size() != 1) throw new IllegalArgumentException("Corresponding records : " + users.size());
        MAdminUser oldEntity = users.get(0);
        MAdminUser newEntity = MAdminUser.fromInsertDomainObj(after);
        newEntity.setId(oldEntity.getId());
        cacheUpdate.add(newEntity);
    }

    @Override
    public int amountOfCachedTransactions() {
        return cacheInsert.amountOfStackedTransactions() +
                cacheUpdate.amountOfStackedTransactions();
    }

    @Override
    public void flush(AppSession session) {
        cacheInsert.flush(session);
        cacheUpdate.flush(session);
    }
}
