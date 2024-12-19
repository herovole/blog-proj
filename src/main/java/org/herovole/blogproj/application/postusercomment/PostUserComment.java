package org.herovole.blogproj.application.postusercomment;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.UserCommentTransactionalDatasource;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.user.PublicIpDatasource;
import org.herovole.blogproj.domain.user.PublicIpTransactionalDatasource;
import org.herovole.blogproj.domain.user.PublicUserDatasource;
import org.herovole.blogproj.domain.user.PublicUserTransactionalDatasource;
import org.herovole.blogproj.domain.user.UniversallyUniqueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PostUserComment {

    private static final Logger logger = LoggerFactory.getLogger(PostUserComment.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final PublicUserDatasource publicUserDatasource;
    private final PublicUserTransactionalDatasource publicUserTransactionalDatasource;
    private final PublicIpDatasource publicIpDatasource;
    private final PublicIpTransactionalDatasource publicIpTransactionalDatasource;
    private final UserCommentDatasource userCommentDatasource;
    private final UserCommentTransactionalDatasource userCommentTransactionalDatasource;

    @Autowired
    public PostUserComment(AppSessionFactory sessionFactory,
                           @Qualifier("userCommentDatasource") UserCommentDatasource userCommentDatasource,
                           UserCommentTransactionalDatasource userCommentTransactionalDatasource) {
        this.sessionFactory = sessionFactory;
        this.userCommentDatasource = userCommentDatasource;
        this.userCommentTransactionalDatasource = userCommentTransactionalDatasource;
    }

    public void process(PostUserCommentInput input) throws Exception {
        logger.info("interpreted post : {}", input);

        UniversallyUniqueId uuId = input.getUuId();
        if (uuId.isEmpty()) {
            uuId = UniversallyUniqueId.generate();
            publicUserTransactionalDatasource.insert(uuId);
        } else {
            Timestamp hasUuIdBannedUntil = publicUserDatasource.isBannedUntil(uuId);
            if (!hasUuIdBannedUntil.isEmpty() && Timestamp.now().precedes(hasUuIdBannedUntil)) {
                logger.info("This user {} is banned until {}",
                        uuId.letterSignature(),
                        hasUuIdBannedUntil);
                // Status Code : 403
                // You are banned until ...
            }

        }

        IPv4Address ip = input.getIPv4Address();
        if (publicIpDatasource.isRecorded(ip)) {
            Timestamp hasIpBannedUntil = publicIpDatasource.isBannedUntil(ip);
            if (!hasIpBannedUntil.isEmpty() && Timestamp.now().precedes(hasIpBannedUntil)) {
                logger.info("This IP {} is banned until {}",
                        ip.toRegularFormat(),
                        hasIpBannedUntil);
                // Status Code : 403
                // You are banned until ...

            }
        }


        CommentUnit comment = input.buildCommentUnit();

        logger.info("insert user comment : {}", comment);
        this.userCommentTransactionalDatasource.insert(comment);
        logger.info("total transaction number : {}", userCommentTransactionalDatasource.amountOfCachedTransactions());

        try (AppSession session = sessionFactory.createSession()) {
            userCommentTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        }
        logger.info("job successful.");

    }
}
