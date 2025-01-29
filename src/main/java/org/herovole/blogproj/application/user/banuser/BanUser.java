package org.herovole.blogproj.application.user.banuser;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.publicuser.PublicUserDatasource;
import org.herovole.blogproj.domain.publicuser.PublicUserTransactionalDatasource;
import org.herovole.blogproj.domain.time.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BanUser {

    private static final Logger logger = LoggerFactory.getLogger(BanUser.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final PublicUserDatasource publicUserDatasource;
    private final PublicUserTransactionalDatasource publicUserTransactionalDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public BanUser(AppSessionFactory sessionFactory,
                   @Qualifier("publicUserDatasource") PublicUserDatasource publicUserDatasource,
                   PublicUserTransactionalDatasource publicUserTransactionalDatasource,
                   GenericPresenter<Object> presenter) {
        this.sessionFactory = sessionFactory;
        this.publicUserDatasource = publicUserDatasource;
        this.publicUserTransactionalDatasource = publicUserTransactionalDatasource;
        this.presenter = presenter;
    }

    public void process(BanUserInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        IntegerPublicUserId userId = input.getUserId();
        if (!this.publicUserDatasource.exists(userId)) {
            presenter.setContent(UseCaseErrorType.GENERIC_USER_ERROR)
                    .setMessage("Specified user doesn't exist.")
                    .interruptProcess();
        }

        Timestamp willBeBannedUntil = Timestamp.now().shiftHours(input.getDays() * 24);
        Timestamp isBannedUntil = this.publicUserDatasource.isBannedUntil(userId);
        if (willBeBannedUntil.precedes(isBannedUntil)) logger.warn("Suspension gets shortened {} => {}",
                isBannedUntil.letterSignatureYyyyMMddSpaceHHmmss(), willBeBannedUntil.letterSignatureYyyyMMddSpaceHHmmss());

        this.publicUserTransactionalDatasource.suspend(userId, willBeBannedUntil);


        try (AppSession session = sessionFactory.createSession()) {
            logger.info("Amount of cached transactions : {}", this.publicUserTransactionalDatasource.amountOfCachedTransactions());
            this.publicUserTransactionalDatasource.flush(session);
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
