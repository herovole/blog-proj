package org.herovole.blogproj.application.ip.banip;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.publicuser.PublicIpDatasource;
import org.herovole.blogproj.domain.publicuser.PublicIpTransactionalDatasource;
import org.herovole.blogproj.domain.time.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BanIp {

    private static final Logger logger = LoggerFactory.getLogger(BanIp.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final PublicIpDatasource publicIpDatasource;
    private final PublicIpTransactionalDatasource publicIpTransactionalDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public BanIp(AppSessionFactory sessionFactory,
                 @Qualifier("publicIpDatasource") PublicIpDatasource publicIpDatasource,
                 PublicIpTransactionalDatasource publicIpTransactionalDatasource,
                 GenericPresenter<Object> presenter) {
        this.sessionFactory = sessionFactory;
        this.publicIpDatasource = publicIpDatasource;
        this.publicIpTransactionalDatasource = publicIpTransactionalDatasource;
        this.presenter = presenter;
    }

    public void flush() throws ApplicationProcessException {
        try (AppSession session = sessionFactory.createSession()) {
            logger.info("Amount of cached transactions : {}", this.publicIpTransactionalDatasource.amountOfCachedTransactions());
            this.publicIpTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        } catch (Exception e) {
            logger.error("transaction failure", e);
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }
    }

    public void process(BanIpInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        IPv4Address ip = input.getIp();
        if (!this.publicIpDatasource.isRecorded(ip)) {
            logger.info("Specified IP doesn't have records. The application builds one.");
            publicIpTransactionalDatasource.insert(ip);
            this.flush();
        }

        Timestamp willBeBannedUntil = Timestamp.now().shiftHours(input.getDays() * 24);
        Timestamp isBannedUntil = this.publicIpDatasource.isBannedUntil(ip);
        if (willBeBannedUntil.precedes(isBannedUntil)) logger.warn("Suspension gets shortened {} => {}",
                isBannedUntil.letterSignatureYyyyMMddSpaceHHmmss(), willBeBannedUntil.letterSignatureYyyyMMddSpaceHHmmss());

        this.publicIpTransactionalDatasource.suspend(ip, willBeBannedUntil);
        this.flush();
        logger.info("job successful.");

    }
}
