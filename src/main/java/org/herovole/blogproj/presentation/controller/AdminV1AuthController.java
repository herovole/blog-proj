package org.herovole.blogproj.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.auth.login.LoginAdmin;
import org.herovole.blogproj.application.auth.login.LoginAdminInput;
import org.herovole.blogproj.application.auth.validateaccesstoken.ValidateAccessToken;
import org.herovole.blogproj.application.auth.validateaccesstoken.ValidateAccessTokenInput;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.AppServletResponse;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.herovole.blogproj.presentation.presenter.LoginAdminPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AdminV1AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AdminV1AuthController.class.getSimpleName());
    private final LoginAdmin loginAdmin;
    private final LoginAdminPresenter loginAdminPresenter;
    private final ValidateAccessToken validateAccessToken;
    private final BasicPresenter basicPresenter;

    @Autowired
    AdminV1AuthController(
            LoginAdmin loginAdmin,
            LoginAdminPresenter loginAdminPresenter,
            ValidateAccessToken validateAccessToken,
            BasicPresenter basicPresenter
    ) {
        this.loginAdmin = loginAdmin;
        this.loginAdminPresenter = loginAdminPresenter;
        this.validateAccessToken = validateAccessToken;
        this.basicPresenter = basicPresenter;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : login (Post) ");
        System.out.println(request);
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        AppServletResponse servletResponse = AppServletResponse.of(httpServletResponse);

        try {
            FormContent formContent = FormContent.of(request);
            LoginAdminInput input = LoginAdminInput.of(servletRequest.getUserIpFromHeader(), formContent);
            this.loginAdmin.process(input);

            AccessToken accessToken = this.loginAdminPresenter.getContent();
            servletResponse.setAccessTokenOnCookie(accessToken);

        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.loginAdminPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.loginAdminPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.loginAdminPresenter.buildResponseEntity();

    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateAccessToken(
            HttpServletRequest httpServletRequest,
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : validate (Post) ");
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);

        try {
            ValidateAccessTokenInput input = ValidateAccessTokenInput.builder()
                    .ip(servletRequest.getUserIpFromHeader())
                    .userId(servletRequest.getUserIdFromAttribute())
                    .accessToken(servletRequest.getAccessTokenFromCookie())
                    .build();
            this.validateAccessToken.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.basicPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.basicPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.basicPresenter.buildResponseEntity();
    }
}
