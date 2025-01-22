package org.herovole.blogproj.application.auth.registeruser;

import jakarta.annotation.PostConstruct;
import org.herovole.blogproj.ConfigFile;
import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.herovole.blogproj.domain.adminuser.AdminUserDatasource;
import org.herovole.blogproj.domain.adminuser.AdminUserRegistrationRequest;
import org.herovole.blogproj.domain.adminuser.AdminUserTransactionalDatasource;
import org.herovole.blogproj.domain.adminuser.CredentialsEncodingFactory;
import org.herovole.blogproj.domain.adminuser.RealAdminUser;
import org.herovole.blogproj.domain.time.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RegisterUser {

    private static final Logger logger = LoggerFactory.getLogger(RegisterUser.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final CredentialsEncodingFactory credentialsEncodingFactory;
    private final AdminUserDatasource adminUserDatasource;
    private final AdminUserTransactionalDatasource adminUserTransactionalDatasource;
    private final GenericPresenter<Object> presenter;
    private final ConfigFile configFile;

    @Autowired
    public RegisterUser(AppSessionFactory sessionFactory,
                        CredentialsEncodingFactory credentialsEncodingFactory,
                        @Qualifier("adminUserDatasource") AdminUserDatasource adminUserDatasource,
                        AdminUserTransactionalDatasource adminUserTransactionalDatasource,
                        GenericPresenter<Object> presenter,
                        ConfigFile configFile
    ) {
        this.sessionFactory = sessionFactory;
        this.credentialsEncodingFactory = credentialsEncodingFactory;
        this.adminUserDatasource = adminUserDatasource;
        this.adminUserTransactionalDatasource = adminUserTransactionalDatasource;
        this.presenter = presenter;
        this.configFile = configFile;
    }

    // This method works only once when the application gets launched.
    @PostConstruct
    public void processToRegisterOwnerAccount() throws ApplicationProcessException {
        AdminUserRegistrationRequest registrationRequest = this.configFile.buildOwnerUserRegistrationRequest();
        logger.info("Checking the existence of the owner account...");
        AdminUser existingUser = this.adminUserDatasource.find(registrationRequest.getUserName());
        if (!existingUser.isEmpty()) {
            logger.info("The owner account has already been registered. (Skipped)");
            return;
        }
        logger.info("Registering the owner account...");

        RegisterUserInput input = RegisterUserInput.of(registrationRequest);
        try {
            this.process(input);
        } catch (Exception e) {
            logger.error("Owner registration failure", e);
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }
    }

    public void process(RegisterUserInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);
        AdminUserRegistrationRequest request = input.getAdminUserRegistrationRequest();

        AdminUser existingUser = this.adminUserDatasource.find(request.getUserName());
        if (!existingUser.isEmpty()) {
            this.presenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR)
                    .setMessage("Duplicate Entry").interruptProcess();
        }

        String credentialsEncoded = this.credentialsEncodingFactory.encodePassword(request.getPassword());
        AdminUser adminUser = RealAdminUser.builder()
                .userName(request.getUserName())
                .role(request.getRole())
                .credentialEncode(credentialsEncoded)
                .accessToken(AccessToken.empty())
                .accessTokenIp(IPv4Address.empty())
                .accessTokenExpiry(Timestamp.empty())
                .build();

        this.adminUserTransactionalDatasource.insert(adminUser);

        try (AppSession session = sessionFactory.createSession()) {
            this.adminUserTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        } catch (Exception e) {
            logger.error("transaction failure", e);
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }
    }
}
