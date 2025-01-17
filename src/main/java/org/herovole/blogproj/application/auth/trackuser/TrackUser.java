package org.herovole.blogproj.application.auth.trackuser;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
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
    private final GenericPresenter<TrackUserOutput> presenter;

    @Autowired
    public TrackUser(AppSessionFactory sessionFactory,
                     @Qualifier("publicUserDatasource") PublicUserDatasource publicUserDatasource,
                     PublicUserTransactionalDatasource publicUserTransactionalDatasource,
                     GenericPresenter<TrackUserOutput> presenter) {
        this.sessionFactory = sessionFactory;
        this.publicUserDatasource = publicUserDatasource;
        this.publicUserTransactionalDatasource = publicUserTransactionalDatasource;
        this.presenter = presenter;
    }


    public void process(TrackUserInput input) throws ApplicationProcessException {
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
        } catch (Exception e) {
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }

        IntegerPublicUserId fixedUserId = this.publicUserDatasource.findIdByUuId(uuId);
        logger.info("job successful.");

        TrackUserOutput output = TrackUserOutput.builder()
                .userId(fixedUserId)
                .uuId(uuId)
                .build();
        this.presenter.setContent(output);
    }
}
