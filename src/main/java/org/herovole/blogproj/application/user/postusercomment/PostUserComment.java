package org.herovole.blogproj.application.user.postusercomment;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.domain.comment.CommentBlackList;
import org.herovole.blogproj.domain.comment.CommentBlackUnit;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.UserCommentTransactionalDatasource;
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
    private final CommentBlackList commentBlackList;

    @Autowired
    public PostUserComment(AppSessionFactory sessionFactory,
                           @Qualifier("userCommentDatasource") UserCommentDatasource userCommentDatasource,
                           UserCommentTransactionalDatasource userCommentTransactionalDatasource,
                           CommentBlackList commentBlackList) {
        this.sessionFactory = sessionFactory;
        this.userCommentDatasource = userCommentDatasource;
        this.userCommentTransactionalDatasource = userCommentTransactionalDatasource;
        this.commentBlackList = commentBlackList;
    }

    public PostUserCommentOutput process(PostUserCommentInput input) throws Exception {
        logger.info("interpreted post : {}", input);

        CommentUnit comment = input.buildCommentUnit();

        CommentBlackUnit detectionHandleName = commentBlackList.detect(comment.getHandleName());
        CommentBlackUnit detectionCommentText = commentBlackList.detect(comment.getCommentText());
        if (!detectionHandleName.isEmpty() || !detectionCommentText.isEmpty()) {
            logger.info("caught to black list pattern(s) : {}, {}", detectionHandleName, detectionCommentText);
            return PostUserCommentOutput.builder().isValid(false).build();
        }

        logger.info("insert user comment : {}", comment);
        this.userCommentTransactionalDatasource.insert(comment);
        logger.info("total transaction number : {}", userCommentTransactionalDatasource.amountOfCachedTransactions());

        try (AppSession session = sessionFactory.createSession()) {
            userCommentTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        }
        logger.info("job successful.");

        return PostUserCommentOutput.builder().isValid(true).build();

    }
}
