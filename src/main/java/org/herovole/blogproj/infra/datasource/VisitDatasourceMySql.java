package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.publicuser.visit.RealVisit;
import org.herovole.blogproj.domain.publicuser.visit.Visit;
import org.herovole.blogproj.domain.publicuser.visit.VisitDatasource;
import org.herovole.blogproj.infra.hibernate.TransactionCache;
import org.herovole.blogproj.infra.jpa.entity.EArticleAccess;
import org.herovole.blogproj.infra.jpa.repository.EArticleAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VisitDatasourceMySql implements VisitDatasource {
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
    protected final EArticleAccessRepository eArticleAccessRepository;

    @Autowired
    public VisitDatasourceMySql(EArticleAccessRepository eArticleAccessRepository) {
        this.eArticleAccessRepository = eArticleAccessRepository;
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
    public void report(Visit visit) {
        if (visit.isEmpty() || !(visit instanceof RealVisit visit1)) throw new IllegalArgumentException();
        EArticleAccess entity = EArticleAccess.fromInsertDomainObj(visit1);
        cacheInsert.add(entity);
    }
}
