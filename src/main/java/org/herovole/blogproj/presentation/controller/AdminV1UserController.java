package org.herovole.blogproj.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.application.user.banuser.BanUser;
import org.herovole.blogproj.application.user.banuser.BanUserInput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class AdminV1UserController {
    private static final Logger logger = LoggerFactory.getLogger(AdminV1UserController.class.getSimpleName());

    private final BanUser banUser;
    private final BasicPresenter banUserPresenter;

    @Autowired
    AdminV1UserController(
            BanUser banUser,
            BasicPresenter banUserPresenter
    ) {
        this.banUser = banUser;
        this.banUserPresenter = banUserPresenter;
    }

    @PutMapping("/{userId}/ban")
    public ResponseEntity<String> banUser(
            HttpServletRequest httpServletRequest,
            @PathVariable int userId,
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : ban user (Put) ");
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        if (!servletRequest.getAdminUserFromAttribute().getRole().bansUsers()) {
            this.banUserPresenter.setUseCaseErrorType(UseCaseErrorType.AUTH_INSUFFICIENT);
            return this.banUserPresenter.buildResponseEntity();
        }

        try {
            FormContent formContent = FormContent.of(request);
            BanUserInput input = BanUserInput.ofFormContent(userId, formContent);
            this.banUser.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.banUserPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Error Application Process Exception : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.banUserPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.banUserPresenter.buildResponseEntity();

    }
}
