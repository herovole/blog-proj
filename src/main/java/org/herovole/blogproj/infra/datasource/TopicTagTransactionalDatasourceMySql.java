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
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TopicTagTransactionalDatasourceMySql extends TopicTagDatasourceMySql implements TopicTagTransactionalDatasource {

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
    private final AtomicInteger maxTopicTagId;

    @Autowired
    public TopicTagTransactionalDatasourceMySql(ATopicTagRepository aTopicTagRepository) {
        super(aTopicTagRepository);
        int maxId = aTopicTagRepository.findMaxId();
        this.maxTopicTagId = new AtomicInteger(maxId);
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

        ATopicTag entityToUpdate = ATopicTag.fromUpdateDomainObj(before);

        cacheUpdate.add(entityToUpdate);

    }
}
