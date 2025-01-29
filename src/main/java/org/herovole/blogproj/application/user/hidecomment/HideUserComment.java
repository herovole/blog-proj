package org.herovole.blogproj.application.user.hidecomment;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.UserCommentTransactionalDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class HideUserComment {

    private static final Logger logger = LoggerFactory.getLogger(HideUserComment.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final UserCommentDatasource userCommentDatasource;
    private final UserCommentTransactionalDatasource userCommentTransactionalDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public HideUserComment(AppSessionFactory sessionFactory,
                           @Qualifier("userCommentDatasource") UserCommentDatasource userCommentDatasource,
                           UserCommentTransactionalDatasource userCommentTransactionalDatasource,
                           GenericPresenter<Object> presenter) {
        this.sessionFactory = sessionFactory;
        this.userCommentDatasource = userCommentDatasource;
        this.userCommentTransactionalDatasource = userCommentTransactionalDatasource;
        this.presenter = presenter;
    }

    public void process(HideUserCommentInput input) throws ApplicationProcessException, NoSuchAlgorithmException {
        logger.info("interpreted post : {}", input);

        IntegerId commentSerialNumber = input.getCommentSerialNumber();
        boolean hides = input.getHides().isTrue();
        CommentUnit commentUnit = this.userCommentDatasource.findByCommentSerialNumber(commentSerialNumber);
        if (commentUnit.isEmpty()) {
            this.presenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR)
                    .setMessage("The specified comment doesn't exist.");
            this.presenter.interruptProcess();
        }

        this.userCommentTransactionalDatasource.hides(commentSerialNumber, hides);
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
