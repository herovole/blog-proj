package org.herovole.blogproj.application.user.rateusercomment.proper;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.UserCommentTransactionalDatasource;
import org.herovole.blogproj.domain.comment.rating.RatingLog;
import org.herovole.blogproj.domain.time.Date;
import org.herovole.blogproj.domain.user.PublicUserDatasource;
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

    @Autowired
    public RateUserComment(AppSessionFactory sessionFactory,
                           @Qualifier("userCommentDatasource") UserCommentDatasource userCommentDatasource,
                           UserCommentTransactionalDatasource userCommentTransactionalDatasource,
                           PublicUserDatasource publicUserDatasource
    ) {
        this.sessionFactory = sessionFactory;
        this.userCommentDatasource = userCommentDatasource;
        this.userCommentTransactionalDatasource = userCommentTransactionalDatasource;
        this.publicUserDatasource = publicUserDatasource;
    }

    public RateUserCommentOutput process(RateUserCommentInput input) throws Exception {
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
            throw new IllegalStateException("The User is trying to cancel what doesn't exist.");
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
        }
        logger.info("job successful.");

        return RateUserCommentOutput.of(true);
    }
}
