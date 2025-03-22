package org.herovole.blogproj.application.auth.loginphase1;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.herovole.blogproj.domain.adminuser.AdminUserDatasource;
import org.herovole.blogproj.domain.adminuser.AdminUserTransactionalDatasource;
import org.herovole.blogproj.domain.adminuser.CredentialsEncodingFactory;
import org.herovole.blogproj.domain.adminuser.EMailService;
import org.herovole.blogproj.domain.adminuser.VerificationCode;
import org.herovole.blogproj.domain.time.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginAdminPhase1 {

    private static final Logger logger = LoggerFactory.getLogger(LoginAdminPhase1.class.getSimpleName());
    private static final int TOKEN_EXPIRY_MINUTES = 15;
    private final AppSessionFactory sessionFactory;
    private final CredentialsEncodingFactory credentialsEncodingFactory;
    private final AdminUserDatasource adminUserDatasource;
    private final AdminUserTransactionalDatasource adminUserTransactionalDatasource;
    private final EMailService emailService;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public LoginAdminPhase1(AppSessionFactory sessionFactory,
                            CredentialsEncodingFactory credentialsEncodingFactory,
                            @Qualifier("adminUserDatasource") AdminUserDatasource adminUserDatasource,
                            AdminUserTransactionalDatasource adminUserTransactionalDatasource,
                            EMailService emailService,
                            GenericPresenter<Object> presenter
    ) {
        this.sessionFactory = sessionFactory;
        this.credentialsEncodingFactory = credentialsEncodingFactory;
        this.adminUserDatasource = adminUserDatasource;
        this.adminUserTransactionalDatasource = adminUserTransactionalDatasource;
        this.emailService = emailService;
        this.presenter = presenter;
    }

    public void process(LoginAdminPhase1Input request) throws ApplicationProcessException {
        logger.info("interpreted post : {}", request);
        AdminUser adminUser = this.adminUserDatasource.find(request.getUserName());

        // IP and Role aren't checked for User/Password logging in.
        if (!this.credentialsEncodingFactory.matches(request.getPassword(), adminUser.getCredentialEncode())) {
            this.presenter
                    .setUseCaseErrorType(UseCaseErrorType.AUTH_FAILURE)
                    .interruptProcess();
        }
        logger.info("Login Phase 1 Confirmed Valid : {}", adminUser.getUserName());

        VerificationCode verificationCode = VerificationCode.generateCode();

        AdminUser newAdminUser = adminUser.appendVerificationCodeInfo(
                verificationCode,
                Timestamp.now().shiftMinutes(TOKEN_EXPIRY_MINUTES)
        ).appendTokenInfo(
                AccessToken.empty(),
                request.getIp(),
                Timestamp.empty()
        );
        this.adminUserTransactionalDatasource.update(adminUser, newAdminUser);

        try (AppSession session = sessionFactory.createSession()) {
            this.adminUserTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        } catch (Exception e) {
            logger.error("transaction failure", e);
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }
        try {
            this.emailService.sendVerificationCode(adminUser.getEMailAddress(), verificationCode);
        } catch(IOException e) {
            logger.error("failed to dispatch an EMail.", e);
        }
    }
}
