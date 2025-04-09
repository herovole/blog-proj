package org.herovole.blogproj.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.application.image.resourceprefix.GetResourcePrefix;
import org.herovole.blogproj.application.site.generaterss2.GenerateRss;
import org.herovole.blogproj.application.site.generaterss2.GenerateRssInput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.herovole.blogproj.presentation.presenter.GetResourcePrefixPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/site")
public class AdminV1SiteManagementController {

    private static final Logger logger = LoggerFactory.getLogger(AdminV1SiteManagementController.class.getSimpleName());

    private final GenerateRss generateRss;
    private final BasicPresenter generateRssPresenter;
    private final GetResourcePrefix getResourcePrefix;
    private final GetResourcePrefixPresenter getResourcePrefixPresenter;

    @Autowired
    public AdminV1SiteManagementController(GenerateRss generateRss, BasicPresenter generateRssPresenter,
                                           GetResourcePrefix getResourcePrefix, GetResourcePrefixPresenter getResourcePrefixPresenter) {
        this.generateRss = generateRss;
        this.generateRssPresenter = generateRssPresenter;
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

    @PostMapping("/rss")
    public ResponseEntity<String> generateRss(
            HttpServletRequest httpServletRequest,
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : generate RSS (post) ");
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        if (!servletRequest.getAdminUserFromAttribute().getRole().editsArticles()) {
            this.generateRssPresenter.setUseCaseErrorType(UseCaseErrorType.AUTH_INSUFFICIENT);
            return this.generateRssPresenter.buildResponseEntity();
        }
        try {
            FormContent formContent = FormContent.of(request);
            GenerateRssInput input = GenerateRssInput.fromFormContent(formContent);
            generateRss.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.generateRssPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Error Application Process Exception : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.generateRssPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.generateRssPresenter.buildResponseEntity();
    }


}
