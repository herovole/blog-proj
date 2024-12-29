package org.herovole.blogproj.application.user.checkuser;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.user.IntegerPublicUserId;
import org.herovole.blogproj.domain.user.PublicIpDatasource;
import org.herovole.blogproj.domain.user.PublicUserDatasource;
import org.herovole.blogproj.domain.user.PublicUserTransactionalDatasource;
import org.herovole.blogproj.domain.user.UniversallyUniqueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CheckUser {

    private static final Logger logger = LoggerFactory.getLogger(CheckUser.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final PublicUserDatasource publicUserDatasource;
    private final PublicUserTransactionalDatasource publicUserTransactionalDatasource;
    private final PublicIpDatasource publicIpDatasource;

    @Autowired
    public CheckUser(AppSessionFactory sessionFactory,
                     @Qualifier("publicUserDatasource") PublicUserDatasource publicUserDatasource,
                     PublicUserTransactionalDatasource publicUserTransactionalDatasource,
                     @Qualifier("publicIpDatasource") PublicIpDatasource publicIpDatasource) {
        this.sessionFactory = sessionFactory;
        this.publicUserDatasource = publicUserDatasource;
        this.publicUserTransactionalDatasource = publicUserTransactionalDatasource;
        this.publicIpDatasource = publicIpDatasource;
    }


    public CheckUserOutput process(CheckUserInput input) throws Exception {
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

            // If the user has been registered, check whether he is banned or not.
        } else {
            Timestamp uuIdBannedUntil = this.publicUserDatasource.isBannedUntil(uuId);
            if (!uuIdBannedUntil.isEmpty() && Timestamp.now().precedes(uuIdBannedUntil)) {
                logger.info("This user {} is banned until {}",
                        uuId,
                        uuIdBannedUntil);
                // Status Code : 403
                // You are banned until ...
                return CheckUserOutput.builder()
                        .uuId(uuId)
                        .timestampBannedUntil(uuIdBannedUntil)
                        .build();
            }

        }

        IPv4Address ip = input.getIPv4Address();
        if (publicIpDatasource.isRecorded(ip)) {
            Timestamp ipBannedUntil = publicIpDatasource.isBannedUntil(ip);
            if (!ipBannedUntil.isEmpty() && Timestamp.now().precedes(ipBannedUntil)) {
                logger.info("This IP {} is banned until {}",
                        ip.toRegularFormat(),
                        ipBannedUntil);
                // Status Code : 403
                // You are banned until ...
                return CheckUserOutput.builder()
                        .uuId(uuId)
                        .timestampBannedUntil(ipBannedUntil)
                        .build();
            }
        }


        try (AppSession session = sessionFactory.createSession()) {
            publicUserTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        }

        IntegerPublicUserId fixedUserId = this.publicUserDatasource.findIdByUuId(uuId);
        logger.info("job successful.");
        return CheckUserOutput.builder()
                .userId(fixedUserId)
                .uuId(uuId)
                .timestampBannedUntil(Timestamp.empty())
                .build();
    }
}
