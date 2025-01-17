package org.herovole.blogproj.application.auth.checkuserban;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.publicuser.PublicIpDatasource;
import org.herovole.blogproj.domain.publicuser.PublicUserDatasource;
import org.herovole.blogproj.domain.publicuser.PublicUserTransactionalDatasource;
import org.herovole.blogproj.domain.time.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CheckUserBan {

    private static final Logger logger = LoggerFactory.getLogger(CheckUserBan.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final PublicUserDatasource publicUserDatasource;
    private final PublicUserTransactionalDatasource publicUserTransactionalDatasource;
    private final PublicIpDatasource publicIpDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public CheckUserBan(AppSessionFactory sessionFactory,
                        @Qualifier("publicUserDatasource") PublicUserDatasource publicUserDatasource,
                        PublicUserTransactionalDatasource publicUserTransactionalDatasource,
                        @Qualifier("publicIpDatasource") PublicIpDatasource publicIpDatasource,
                        GenericPresenter<Object> presenter) {
        this.sessionFactory = sessionFactory;
        this.publicUserDatasource = publicUserDatasource;
        this.publicUserTransactionalDatasource = publicUserTransactionalDatasource;
        this.publicIpDatasource = publicIpDatasource;
        this.presenter = presenter;
    }


    public void process(CheckUserBanInput input) throws Exception {
        logger.info("interpreted post : {}", input);
        IntegerPublicUserId userId = input.getUserId();

        // Assume that TrackUser has already been executed.
        // = User has already got UserID assigned.

        // Check suspension state by UserID.
        Timestamp uuIdBannedUntil = this.publicUserDatasource.isBannedUntil(userId);
        if (!uuIdBannedUntil.isEmpty() && Timestamp.now().precedes(uuIdBannedUntil)) {
            logger.info("This user {} is banned until {}",
                    userId,
                    uuIdBannedUntil);
            // Status Code : 403
            // You are banned until ...
            this.presenter.setUseCaseErrorType(UseCaseErrorType.AUTH_FAILURE)
                    .setTimestampBannedUntil(uuIdBannedUntil)
                    .interruptProcess();
        }

        // Check suspension state by IPV4Address.
        IPv4Address ip = input.getIPv4Address();
        if (publicIpDatasource.isRecorded(ip)) {
            Timestamp ipBannedUntil = publicIpDatasource.isBannedUntil(ip);
            if (!ipBannedUntil.isEmpty() && Timestamp.now().precedes(ipBannedUntil)) {
                logger.info("This IP {} is banned until {}",
                        ip.toRegularFormat(),
                        ipBannedUntil);
                // Status Code : 403
                // You are banned until ...
                this.presenter.setUseCaseErrorType(UseCaseErrorType.AUTH_FAILURE)
                        .setTimestampBannedUntil(ipBannedUntil)
                        .interruptProcess();
            }
        }

        try (AppSession session = sessionFactory.createSession()) {
            publicUserTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        }

    }
}
