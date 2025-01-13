package org.herovole.blogproj.application.user.postusercomment.proper;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.comment.TextBlackUnit;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.UserCommentTransactionalDatasource;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;
import org.herovole.blogproj.domain.publicuser.PublicUserDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PostUserComment {

    private static final Logger logger = LoggerFactory.getLogger(PostUserComment.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final UserCommentDatasource userCommentDatasource;
    private final UserCommentTransactionalDatasource userCommentTransactionalDatasource;
    private final PublicUserDatasource publicUserDatasource;
    private final TextBlackList textBlackList;
    private final DailyUserIdFactory dailyUserIdFactory;

    @Autowired
    public PostUserComment(AppSessionFactory sessionFactory,
                           @Qualifier("userCommentDatasource") UserCommentDatasource userCommentDatasource,
                           UserCommentTransactionalDatasource userCommentTransactionalDatasource,
                           PublicUserDatasource publicUserDatasource,
                           TextBlackList textBlackList,
                           DailyUserIdFactory dailyUserIdFactory
    ) {
        this.sessionFactory = sessionFactory;
        this.userCommentDatasource = userCommentDatasource;
        this.userCommentTransactionalDatasource = userCommentTransactionalDatasource;
        this.publicUserDatasource = publicUserDatasource;
        this.textBlackList = textBlackList;
        this.dailyUserIdFactory = dailyUserIdFactory;
    }

    public PostUserCommentOutput process(PostUserCommentInput input) throws Exception {
        logger.info("interpreted post : {}", input);

        CommentUnit comment = input.buildCommentUnit();

        TextBlackUnit detectionHandleName = textBlackList.detect(comment.getHandleName());
        TextBlackUnit detectionCommentText = textBlackList.detect(comment.getCommentText());
        if (!detectionHandleName.isEmpty() || !detectionCommentText.isEmpty()) {
            logger.info("caught to black list pattern(s) : {}, {}", detectionHandleName, detectionCommentText);
            return PostUserCommentOutput.of(false);
        }

        CommentUnit comment2 = comment.appendDailyUserId(this.dailyUserIdFactory);

        logger.info("insert user comment : {}", comment2);
        this.userCommentTransactionalDatasource.insert(comment2);
        logger.info("total transaction number : {}", userCommentTransactionalDatasource.amountOfCachedTransactions());

        try (AppSession session = sessionFactory.createSession()) {
            userCommentTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        }
        logger.info("job successful.");

        return PostUserCommentOutput.of(true);

    }
}
