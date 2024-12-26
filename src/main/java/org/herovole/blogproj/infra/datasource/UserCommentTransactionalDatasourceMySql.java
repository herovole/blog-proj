package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.RealUserCommentUnit;
import org.herovole.blogproj.domain.comment.UserCommentTransactionalDatasource;
import org.herovole.blogproj.infra.hibernate.TransactionCache;
import org.herovole.blogproj.infra.jpa.entity.EUserComment;
import org.herovole.blogproj.infra.jpa.repository.EUserCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UserCommentTransactionalDatasourceMySql extends UserCommentDatasourceMySql implements UserCommentTransactionalDatasource {
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

    private static final Map<IntegerId, AtomicInteger> mapMaxCommentIdByArticleId = new HashMap<>();

    private IntegerId incrementAndGetInArticleCommentId(IntegerId articleId) {
        if (!mapMaxCommentIdByArticleId.containsKey(articleId)) {
            Integer maxId = eUserCommentRepository.findMaxCommentId(articleId.intMemorySignature());
            mapMaxCommentIdByArticleId.put(articleId,
                    maxId == null ? new AtomicInteger(0) : new AtomicInteger(maxId)
            );
        }
        return IntegerId.valueOf(mapMaxCommentIdByArticleId.get(articleId).incrementAndGet());
    }

    @Autowired
    public UserCommentTransactionalDatasourceMySql(EUserCommentRepository eUserCommentRepository) {
        super(eUserCommentRepository);
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

    @Override
    public void insert(CommentUnit commentUnit) {
        if (!(commentUnit instanceof RealUserCommentUnit)) throw new IllegalArgumentException();
        IntegerId commentId = this.incrementAndGetInArticleCommentId(commentUnit.getArticleId());
        EUserComment eUserComment = EUserComment.fromInsertDomainObj(commentId, commentUnit);
        cacheInsert.add(eUserComment);
    }
}
