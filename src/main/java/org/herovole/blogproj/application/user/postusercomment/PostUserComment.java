package org.herovole.blogproj.application.user.postusercomment;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.TextBlackUnit;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.UserCommentTransactionalDatasource;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;
import org.herovole.blogproj.domain.publicuser.PublicUserDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class PostUserComment {

    private static final Logger logger = LoggerFactory.getLogger(PostUserComment.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final UserCommentDatasource userCommentDatasource;
    private final UserCommentTransactionalDatasource userCommentTransactionalDatasource;
    private final PublicUserDatasource publicUserDatasource;
    private final TextBlackList textBlackList;
    private final DailyUserIdFactory dailyUserIdFactory;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public PostUserComment(AppSessionFactory sessionFactory,
                           @Qualifier("userCommentDatasource") UserCommentDatasource userCommentDatasource,
                           UserCommentTransactionalDatasource userCommentTransactionalDatasource,
                           PublicUserDatasource publicUserDatasource,
                           TextBlackList textBlackList,
                           DailyUserIdFactory dailyUserIdFactory,
                           GenericPresenter<Object> presenter) {
        this.sessionFactory = sessionFactory;
        this.userCommentDatasource = userCommentDatasource;
        this.userCommentTransactionalDatasource = userCommentTransactionalDatasource;
        this.publicUserDatasource = publicUserDatasource;
        this.textBlackList = textBlackList;
        this.dailyUserIdFactory = dailyUserIdFactory;
        this.presenter = presenter;
    }

    public void process(PostUserCommentInput input) throws ApplicationProcessException, NoSuchAlgorithmException {
        logger.info("interpreted post : {}", input);

        CommentUnit comment = input.buildCommentUnit();

        TextBlackUnit detectionHandleName = textBlackList.detectHumanThreat(comment.getHandleName());
        TextBlackUnit detectionCommentText = textBlackList.detectHumanThreat(comment.getCommentText());
        if (!detectionHandleName.isEmpty() || !detectionCommentText.isEmpty()) {
            logger.info("caught to black list pattern(s) : {}, {}", detectionHandleName, detectionCommentText);
            presenter.setUseCaseErrorType(UseCaseErrorType.HUMAN_THREATENING_PHRASE)
                    .interruptProcess();
        }

        CommentUnit comment2 = comment.appendDailyUserId(this.dailyUserIdFactory);

        logger.info("insert user comment : {}", comment2);
        this.userCommentTransactionalDatasource.insert(comment2);
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
