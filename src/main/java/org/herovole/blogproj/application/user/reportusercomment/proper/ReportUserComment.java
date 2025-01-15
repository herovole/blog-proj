package org.herovole.blogproj.application.user.reportusercomment.proper;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.comment.TextBlackUnit;
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
    private final TextBlackList textBlackList;

    @Autowired
    public ReportUserComment(AppSessionFactory sessionFactory,
                             UserCommentTransactionalDatasource userCommentTransactionalDatasource,
                             TextBlackList textBlackList
    ) {
        this.sessionFactory = sessionFactory;
        this.userCommentTransactionalDatasource = userCommentTransactionalDatasource;
        this.textBlackList = textBlackList;
    }

    public ReportUserCommentOutput process(ReportUserCommentInput input) throws Exception {
        logger.info("interpreted post : {}", input);

        Reporting reporting = input.buildReporting();

        TextBlackUnit detectionCommentText = textBlackList.detectHumanThreat(reporting.getReportingText());
        if (!detectionCommentText.isEmpty()) {
            logger.info("caught to black list pattern(s) : {}", detectionCommentText);
            return ReportUserCommentOutput.of(false);
        }
        logger.info("inserting report : {}", reporting);
        this.userCommentTransactionalDatasource.insertReport(reporting);
        logger.info("total transaction number : {}", userCommentTransactionalDatasource.amountOfCachedTransactions());

        try (AppSession session = sessionFactory.createSession()) {
            userCommentTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        }
        logger.info("job successful.");

        return ReportUserCommentOutput.of(true);

    }
}
