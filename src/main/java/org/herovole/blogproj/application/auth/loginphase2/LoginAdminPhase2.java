package org.herovole.blogproj.application.auth.loginphase2;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.meta.SiteInformation;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.adminuser.AccessTokenFactory;
import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.herovole.blogproj.domain.adminuser.AdminUserDatasource;
import org.herovole.blogproj.domain.adminuser.AdminUserTransactionalDatasource;
import org.herovole.blogproj.domain.adminuser.CredentialsEncodingFactory;
import org.herovole.blogproj.domain.adminuser.VerificationCode;
import org.herovole.blogproj.domain.time.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LoginAdminPhase2 {

    private static final Logger logger = LoggerFactory.getLogger(LoginAdminPhase2.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final SiteInformation siteInformation;
    private final CredentialsEncodingFactory credentialsEncodingFactory;
    private final AccessTokenFactory accessTokenFactory;
    private final AdminUserDatasource adminUserDatasource;
    private final AdminUserTransactionalDatasource adminUserTransactionalDatasource;
    private final GenericPresenter<AccessToken> presenter;

    @Autowired
    public LoginAdminPhase2(AppSessionFactory sessionFactory,
                            SiteInformation siteInformation,
                            CredentialsEncodingFactory credentialsEncodingFactory,
                            AccessTokenFactory accessTokenFactory,
                            @Qualifier("adminUserDatasource") AdminUserDatasource adminUserDatasource,
                            AdminUserTransactionalDatasource adminUserTransactionalDatasource,
                            GenericPresenter<AccessToken> presenter
    ) {
        this.sessionFactory = sessionFactory;
        this.siteInformation = siteInformation;
        this.credentialsEncodingFactory = credentialsEncodingFactory;
        this.accessTokenFactory = accessTokenFactory;
        this.adminUserDatasource = adminUserDatasource;
        this.adminUserTransactionalDatasource = adminUserTransactionalDatasource;
        this.presenter = presenter;
    }

    public void process(LoginAdminPhase2Input request) throws ApplicationProcessException {
        logger.info("interpreted post : {}", request);


        AdminUser adminUser = this.adminUserDatasource.find(request.getUserName());
        if (adminUser.isEmpty()) {
            this.presenter
                    .setUseCaseErrorType(UseCaseErrorType.AUTH_FAILURE)
                    .interruptProcess();
        }

        // AGAIN : IP and Role aren't checked for User/Password logging in.
        if (!this.credentialsEncodingFactory.matches(request.getPassword(), adminUser.getCredentialEncode())) {
            this.presenter
                    .setUseCaseErrorType(UseCaseErrorType.AUTH_FAILURE)
                    .interruptProcess();
        }

        //Skip verifying verification code if the environment is local or staging
        if (!siteInformation.isLocal() && !siteInformation.isStaging()) {
            // Check Verification Code
            if (!adminUser.hasCoherentVerificationCode(request.getVerificationCode()) ||
                    adminUser.getVerificationCodeExpiry().isEmpty() ||
                    adminUser.getVerificationCodeExpiry().precedes(Timestamp.now())) {
                this.presenter
                        .setUseCaseErrorType(UseCaseErrorType.AUTH_FAILURE)
                        .interruptProcess();
            }
            logger.info("Login Phase 2 Confirmed Valid : {}", adminUser.getUserName());
        }

        AccessToken accessToken = this.accessTokenFactory.generateToken(adminUser);
        AdminUser newAdminUser = adminUser.appendVerificationCodeInfo(
                VerificationCode.empty(),
                Timestamp.empty()
        ).appendTokenInfo(
                accessToken,
                request.getIp(),
                this.accessTokenFactory.getExpectedExpirationTime()
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

        this.presenter.setContent(accessToken);
    }
}
