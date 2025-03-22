package org.herovole.blogproj.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.application.ip.banip.BanIp;
import org.herovole.blogproj.application.ip.banip.BanIpInput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ip")
public class AdminV1IpController {
    private static final Logger logger = LoggerFactory.getLogger(AdminV1IpController.class.getSimpleName());

    private final BanIp banIp;
    private final BasicPresenter banIpPresenter;

    @Autowired
    AdminV1IpController(
            BanIp banIp,
            BasicPresenter banIpPresenter
    ) {
        this.banIp = banIp;
        this.banIpPresenter = banIpPresenter;
    }

    @PutMapping("/ban")
    public ResponseEntity<String> banIp(
            HttpServletRequest httpServletRequest,
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : ban user (Put) ");
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        if (!servletRequest.getAdminUserFromAttribute().getRole().bansUsers()) {
            this.banIpPresenter.setUseCaseErrorType(UseCaseErrorType.AUTH_INSUFFICIENT);
            return this.banIpPresenter.buildResponseEntity();
        }

        try {
            FormContent formContent = FormContent.of(request);
            BanIpInput input = BanIpInput.ofFormContent(formContent);
            this.banIp.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.banIpPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Error Application Process Exception : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.banIpPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.banIpPresenter.buildResponseEntity();

    }
}
