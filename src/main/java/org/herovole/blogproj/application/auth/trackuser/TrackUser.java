package org.herovole.blogproj.application.auth.trackuser;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.publicuser.PublicUserDatasource;
import org.herovole.blogproj.domain.publicuser.PublicUserTransactionalDatasource;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TrackUser {

    private static final Logger logger = LoggerFactory.getLogger(TrackUser.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final PublicUserDatasource publicUserDatasource;
    private final PublicUserTransactionalDatasource publicUserTransactionalDatasource;

    @Autowired
    public TrackUser(AppSessionFactory sessionFactory,
                     @Qualifier("publicUserDatasource") PublicUserDatasource publicUserDatasource,
                     PublicUserTransactionalDatasource publicUserTransactionalDatasource) {
        this.sessionFactory = sessionFactory;
        this.publicUserDatasource = publicUserDatasource;
        this.publicUserTransactionalDatasource = publicUserTransactionalDatasource;
    }


    public TrackUserOutput process(TrackUserInput input) throws Exception {
        logger.info("interpreted post : {}", input);

        // Check if the user has already been registered.
        UniversallyUniqueId uuId = input.getUuId();
        IntegerId userId = IntegerId.empty();
        if (!uuId.isEmpty()) {
            userId = publicUserDatasource.findIdByUuId(uuId);
            if (userId.isEmpty()) {
                logger.warn("Uncanny UUID : {} hasn't been issued yet.", uuId);
            }
        }

        // If the user hasn't been registered, register his info.
        if (uuId.isEmpty() || userId.isEmpty()) {
            uuId = UniversallyUniqueId.generate();
            this.publicUserTransactionalDatasource.insert(uuId);
            logger.info("new UUID has been issued : {}", uuId);
        }

        try (AppSession session = sessionFactory.createSession()) {
            publicUserTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        }

        IntegerPublicUserId fixedUserId = this.publicUserDatasource.findIdByUuId(uuId);
        logger.info("job successful.");
        return TrackUserOutput.builder()
                .userId(fixedUserId)
                .uuId(uuId)
                .build();
    }
}
