package org.herovole.blogproj.application.auth.validateaccesstoken;

import io.jsonwebtoken.security.SignatureException;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.adminuser.AccessTokenFactory;
import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.herovole.blogproj.domain.adminuser.AdminUserDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ValidateAccessToken {

    private static final Logger logger = LoggerFactory.getLogger(ValidateAccessToken.class.getSimpleName());

    private final AccessTokenFactory accessTokenFactory;
    private final AdminUserDatasource adminUserDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public ValidateAccessToken(AccessTokenFactory accessTokenFactory,
                               @Qualifier("adminUserDatasource") AdminUserDatasource adminUserDatasource,
                               GenericPresenter<Object> presenter) {
        this.accessTokenFactory = accessTokenFactory;
        this.adminUserDatasource = adminUserDatasource;
        this.presenter = presenter;
    }

    public void process(ValidateAccessTokenInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        try {
            AdminUser claim = accessTokenFactory.validateToken(input.getAccessToken());
            AdminUser adminUser = this.adminUserDatasource.find(input.getAccessToken());
            if (!claim.isCoherentTo(adminUser)) {
                presenter.setUseCaseErrorType(UseCaseErrorType.AUTH_FAILURE).interruptProcess();
            }
        } catch (SignatureException e) {
            presenter.setUseCaseErrorType(UseCaseErrorType.AUTH_FAILURE).interruptProcess();
        }
    }
}
