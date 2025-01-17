package org.herovole.blogproj.application.user.reportusercomment;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.comment.UserCommentTransactionalDatasource;
import org.herovole.blogproj.domain.comment.reporting.Reporting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportUserComment {

    private static final Logger logger = LoggerFactory.getLogger(ReportUserComment.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final UserCommentTransactionalDatasource userCommentTransactionalDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public ReportUserComment(AppSessionFactory sessionFactory,
                             UserCommentTransactionalDatasource userCommentTransactionalDatasource,
                             GenericPresenter<Object> presenter) {
        this.sessionFactory = sessionFactory;
        this.userCommentTransactionalDatasource = userCommentTransactionalDatasource;
        this.presenter = presenter;
    }

    public void process(ReportUserCommentInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);
        Reporting reporting = input.buildReporting();

        logger.info("inserting report : {}", reporting);
        this.userCommentTransactionalDatasource.insertReport(reporting);

        logger.info("total transaction number : {}", userCommentTransactionalDatasource.amountOfCachedTransactions());
        try (AppSession session = sessionFactory.createSession()) {
            userCommentTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        } catch (Exception e) {
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }
        logger.info("job successful.");
    }
}
