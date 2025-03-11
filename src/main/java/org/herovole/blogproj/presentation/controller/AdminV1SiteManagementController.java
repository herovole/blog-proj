package org.herovole.blogproj.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.application.image.resourceprefix.GetResourcePrefix;
import org.herovole.blogproj.application.site.generaterss2.GenerateRss2;
import org.herovole.blogproj.application.site.generaterss2.GenerateRss2Input;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.herovole.blogproj.presentation.presenter.GetResourcePrefixPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/site")
public class AdminV1SiteManagementController {

    private static final Logger logger = LoggerFactory.getLogger(AdminV1SiteManagementController.class.getSimpleName());

    private final GenerateRss2 generateRss2;
    private final BasicPresenter generateRss2Presenter;
    private final GetResourcePrefix getResourcePrefix;
    private final GetResourcePrefixPresenter getResourcePrefixPresenter;

    @Autowired
    public AdminV1SiteManagementController(GenerateRss2 generateRss2, BasicPresenter generateRss2Presenter,
                                           GetResourcePrefix getResourcePrefix, GetResourcePrefixPresenter getResourcePrefixPresenter) {
        this.generateRss2 = generateRss2;
        this.generateRss2Presenter = generateRss2Presenter;
        this.getResourcePrefix = getResourcePrefix;
        this.getResourcePrefixPresenter = getResourcePrefixPresenter;
    }

    @GetMapping("/prefix")
    public ResponseEntity<String> getResourcePrefix() {

        try {
            getResourcePrefix.process();
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.getResourcePrefixPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Error Application Process Exception : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.getResourcePrefixPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.getResourcePrefixPresenter.buildResponseEntity();
    }

    @PostMapping
    public ResponseEntity<String> generateRss2(
            HttpServletRequest httpServletRequest) {
        logger.info("Endpoint : generate RSS 2 (post) ");
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        if (!servletRequest.getAdminUserFromAttribute().getRole().editsArticles()) {
            this.generateRss2Presenter.setUseCaseErrorType(UseCaseErrorType.AUTH_INSUFFICIENT);
            return this.generateRss2Presenter.buildResponseEntity();
        }
        try {
            GenerateRss2Input input = GenerateRss2Input.builder().build();
            generateRss2.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.generateRss2Presenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Error Application Process Exception : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.generateRss2Presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.generateRss2Presenter.buildResponseEntity();
    }


}
