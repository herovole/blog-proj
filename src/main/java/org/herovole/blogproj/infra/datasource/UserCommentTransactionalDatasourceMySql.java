package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.RealUserCommentUnit;
import org.herovole.blogproj.domain.comment.UserCommentTransactionalDatasource;
import org.herovole.blogproj.domain.comment.rating.RatingLog;
import org.herovole.blogproj.domain.comment.reporting.Reporting;
import org.herovole.blogproj.infra.hibernate.TransactionCache;
import org.herovole.blogproj.infra.jpa.entity.EUserComment;
import org.herovole.blogproj.infra.jpa.entity.EUserCommentRating;
import org.herovole.blogproj.infra.jpa.entity.EUserCommentReport;
import org.herovole.blogproj.infra.jpa.repository.EUserCommentRatingRepository;
import org.herovole.blogproj.infra.jpa.repository.EUserCommentReportRepository;
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
                    maxId == null ? new AtomicInteger(1000) : new AtomicInteger(maxId)
            );
        }
        return IntegerId.valueOf(mapMaxCommentIdByArticleId.get(articleId).incrementAndGet());
    }

    @Autowired
    public UserCommentTransactionalDatasourceMySql(
            EUserCommentRepository eUserCommentRepository,
            EUserCommentRatingRepository eUserCommentRatingRepository,
            EUserCommentReportRepository eUserCommentReportRepository) {
        super(eUserCommentRepository, eUserCommentRatingRepository, eUserCommentReportRepository);
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

    @Override
    public void insertRating(RatingLog ratingLog) {
        if (ratingLog.isEmpty()) throw new IllegalArgumentException();
        EUserCommentRating eUserCommentRating = EUserCommentRating.fromDomainObjectInsert(ratingLog);
        cacheInsert.add(eUserCommentRating);
    }

    @Override
    public void updateRating(RatingLog before, RatingLog after) {
        if (after.isEmpty()) throw new IllegalArgumentException();
        EUserCommentRating eUserCommentRating = EUserCommentRating.fromDomainObjectUpdate(before.getId(), after);
        cacheUpdate.add(eUserCommentRating);
    }

    @Override
    public void insertReport(Reporting report) {
        if (report.isEmpty()) throw new IllegalArgumentException();
        EUserCommentReport eUserCommentReport = EUserCommentReport.fromDomainObjectInsert(report);
        cacheInsert.add(eUserCommentReport);
    }

    @Override
    public void hides(IntegerId commentSerialNumber, boolean hides) {
        if (commentSerialNumber.isEmpty()) throw new IllegalArgumentException();
        EUserComment entity = eUserCommentRepository.findBySerialNumber(commentSerialNumber.longMemorySignature());
        entity.setHidden(hides);
        cacheUpdate.add(entity);
    }

    @Override
    public void handleReport(IntegerId reportId, boolean handles) {
        if (reportId.isEmpty()) throw new IllegalArgumentException();
        EUserCommentReport entity = eUserCommentReportRepository.findByReportId(reportId.longMemorySignature());
        entity.setHandled(handles);
        cacheUpdate.add(entity);
    }
}
