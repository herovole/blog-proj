package org.herovole.blogproj.application.user.rateusercomment;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.UserCommentTransactionalDatasource;
import org.herovole.blogproj.domain.comment.rating.RatingLog;
import org.herovole.blogproj.domain.publicuser.PublicUserDatasource;
import org.herovole.blogproj.domain.time.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RateUserComment {

    private static final Logger logger = LoggerFactory.getLogger(RateUserComment.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final UserCommentDatasource userCommentDatasource;
    private final UserCommentTransactionalDatasource userCommentTransactionalDatasource;
    private final PublicUserDatasource publicUserDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public RateUserComment(AppSessionFactory sessionFactory,
                           @Qualifier("userCommentDatasource") UserCommentDatasource userCommentDatasource,
                           UserCommentTransactionalDatasource userCommentTransactionalDatasource,
                           PublicUserDatasource publicUserDatasource,
                           GenericPresenter<Object> presenter) {
        this.sessionFactory = sessionFactory;
        this.userCommentDatasource = userCommentDatasource;
        this.userCommentTransactionalDatasource = userCommentTransactionalDatasource;
        this.publicUserDatasource = publicUserDatasource;
        this.presenter = presenter;
    }

    public void process(RateUserCommentInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        RatingLog ratingLog = input.buildRatingLog();
        RatingLog pastRatingLogByUserId = this.userCommentDatasource.findActiveRatingHistory(ratingLog.getCommentSerialNumber(), ratingLog.getPublicUserId());
        RatingLog pastRatingLogByIp = this.userCommentDatasource.findActiveRatingHistory(ratingLog.getCommentSerialNumber(), ratingLog.getIPv4Address(), Date.today());

        if (pastRatingLogByUserId.isEmpty()
                && pastRatingLogByIp.isEmpty()
                && ratingLog.hasActualRating()) {
            this.userCommentTransactionalDatasource.insertRating(ratingLog);
        }
        if (pastRatingLogByUserId.isEmpty()
                && pastRatingLogByIp.isEmpty()
                && !ratingLog.hasActualRating()) {
            this.presenter
                    .setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR)
                    .setMessage("The User is trying to cancel what doesn't exist.")
                    .interruptProcess();
        }
        if (!pastRatingLogByUserId.isEmpty()
                && ratingLog.hasActualRating()) {
            this.userCommentTransactionalDatasource.updateRating(pastRatingLogByUserId, ratingLog);
        }
        if (pastRatingLogByUserId.isEmpty()
                && !pastRatingLogByIp.isEmpty()
                && ratingLog.hasActualRating()) {
            this.userCommentTransactionalDatasource.updateRating(pastRatingLogByIp, ratingLog);
        }

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
