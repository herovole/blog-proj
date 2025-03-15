package org.herovole.blogproj.application.user.reportusercomment;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.application.user.postusercomment.PostUserCommentDurationConfig;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.UserCommentTransactionalDatasource;
import org.herovole.blogproj.domain.comment.reporting.Reporting;
import org.herovole.blogproj.domain.time.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReportUserComment {

    private static final Logger logger = LoggerFactory.getLogger(ReportUserComment.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final UserCommentDatasource userCommentDatasource;
    private final UserCommentTransactionalDatasource userCommentTransactionalDatasource;
    private final GenericPresenter<Object> presenter;
    private final PostUserCommentDurationConfig postUserCommentDurationConfig;

    @Autowired
    public ReportUserComment(AppSessionFactory sessionFactory,
                             @Qualifier("userCommentDatasource") UserCommentDatasource userCommentDatasource,
                             UserCommentTransactionalDatasource userCommentTransactionalDatasource,
                             GenericPresenter<Object> presenter,
                             PostUserCommentDurationConfig postUserCommentDurationConfig
    ) {
        this.sessionFactory = sessionFactory;
        this.userCommentDatasource = userCommentDatasource;
        this.userCommentTransactionalDatasource = userCommentTransactionalDatasource;
        this.presenter = presenter;
        this.postUserCommentDurationConfig = postUserCommentDurationConfig;
    }

    public void process(ReportUserCommentInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);
        Reporting reporting = input.buildReporting();

        Reporting lastReport = this.userCommentDatasource.findLastReport(reporting.getPublicUserId());
        if (!lastReport.isEmpty() && lastReport.getPostedSecondsAgo() < postUserCommentDurationConfig.secondsIntervalGeneralArticle()) {
            logger.error("Frequent Posts, last post : {}, now : {}", lastReport.getReportTimestamp(), Timestamp.now());
            presenter.setUseCaseErrorType(UseCaseErrorType.FREQUENT_POSTS).interruptProcess();
        }

        logger.info("inserting report : {}", reporting);
        this.userCommentTransactionalDatasource.insertReport(reporting);

        logger.info("total transaction number : {}", userCommentTransactionalDatasource.amountOfCachedTransactions());
        try (AppSession session = sessionFactory.createSession()) {
            userCommentTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        } catch (Exception e) {
            logger.error("transaction failure", e);
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }
        logger.info("job successful.");
    }
}
