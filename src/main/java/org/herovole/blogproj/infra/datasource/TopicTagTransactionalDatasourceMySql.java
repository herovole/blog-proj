package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.tag.topic.RealTagUnit;
import org.herovole.blogproj.domain.tag.topic.TagUnit;
import org.herovole.blogproj.domain.tag.topic.TopicTagTransactionalDatasource;
import org.herovole.blogproj.infra.hibernate.TransactionCache;
import org.herovole.blogproj.infra.jpa.entity.ATopicTag;
import org.herovole.blogproj.infra.jpa.entity.EmptyRecordException;
import org.herovole.blogproj.infra.jpa.entity.IncompatibleUpdateException;
import org.herovole.blogproj.infra.jpa.repository.ATopicTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class TopicTagTransactionalDatasourceMySql extends TopicTagDatasourceMySql implements TopicTagTransactionalDatasource {

    private static AtomicInteger maxTopicTagId;
    private final TransactionCache<Object> cacheInsert = new TransactionCache<>() {
        @Override
        protected void doTransaction(Object transaction, AppSession session) {
            session.insert(transaction);
        }
    };
    private final TransactionCache<Object> cacheUpdate = new TransactionCache<>() {
        @Override
        protected void doTransaction(Object transaction, AppSession session) {
            session.update(transaction);
        }
    };
    private final TransactionCache<String> cacheDelete = new TransactionCache<>() {
        @Override
        protected void doTransaction(String transaction, AppSession session) {
            session.executeSql(transaction);
        }
    };

    @Autowired
    public TopicTagTransactionalDatasourceMySql(ATopicTagRepository aTopicTagRepository) {
        super(aTopicTagRepository);
        Integer maxId = aTopicTagRepository.findMaxId();
        maxTopicTagId = maxId == null ? new AtomicInteger(0) : new AtomicInteger(maxId);
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
    public void insert(TagUnit tagUnit) {
        if (tagUnit.isEmpty()) return;
        IntegerId tagUnitId = IntegerId.valueOf(maxTopicTagId.incrementAndGet());
        ATopicTag entity = ATopicTag.fromInsertDomainObj(tagUnitId, tagUnit);
        cacheInsert.add(entity);
    }

    @Override
    public void update(TagUnit before, TagUnit after) {
        if (after.isEmpty() || before.isEmpty()) throw new EmptyRecordException();
        RealTagUnit before1 = (RealTagUnit) before;
        RealTagUnit after1 = (RealTagUnit) after;
        if (!before1.getId().equals(after1.getId())) throw new IncompatibleUpdateException();

        ATopicTag entityToUpdate = ATopicTag.fromUpdateDomainObj(after);

        cacheUpdate.add(entityToUpdate);

    }
}
