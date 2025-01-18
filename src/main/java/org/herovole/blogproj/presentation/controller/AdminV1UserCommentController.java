package org.herovole.blogproj.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.application.user.postusercomment.PostUserComment;
import org.herovole.blogproj.application.user.postusercomment.PostUserCommentInput;
import org.herovole.blogproj.application.user.rateusercomment.RateUserComment;
import org.herovole.blogproj.application.user.rateusercomment.RateUserCommentInput;
import org.herovole.blogproj.application.user.reportusercomment.ReportUserComment;
import org.herovole.blogproj.application.user.reportusercomment.ReportUserCommentInput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/usercomments")
public class AdminV1UserCommentController {
    private static final Logger logger = LoggerFactory.getLogger(AdminV1UserCommentController.class.getSimpleName());

    private final PostUserComment postUserComment;
    private final BasicPresenter postUserCommentPresenter;
    private final RateUserComment rateUserComment;
    private final BasicPresenter rateUserCommentPresenter;
    private final ReportUserComment reportUserComment;
    private final BasicPresenter reportUserCommentPresenter;

    @Autowired
    AdminV1UserCommentController(
            PostUserComment postUserComment,
            BasicPresenter postUserCommentPresenter, RateUserComment rateUserComment,
            BasicPresenter rateUserCommentPresenter, ReportUserComment reportUserComment,
            BasicPresenter reportUserCommentPresenter) {
        this.postUserComment = postUserComment;
        this.postUserCommentPresenter = postUserCommentPresenter;
        this.rateUserComment = rateUserComment;
        this.rateUserCommentPresenter = rateUserCommentPresenter;
        this.reportUserComment = reportUserComment;
        this.reportUserCommentPresenter = reportUserCommentPresenter;
    }

    @PostMapping
    public ResponseEntity<String> postComment(
            @RequestBody Map<String, String> request,
            HttpServletRequest httpServletRequest
    ) {
        logger.info("Endpoint : userComments (Post) ");

        try {
            // Check user status
            AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
            FormContent formContent = FormContent.of(request);
            System.out.println(servletRequest.getUserIdFromAttribute());
            PostUserCommentInput input = new PostUserCommentInput.Builder()
                    .iPv4Address(servletRequest.getUserIpFromHeader())
                    .userId(servletRequest.getUserIdFromAttribute())
                    .formContent(formContent)
                    .build();

            logger.info("postComment input : {}", input);
            this.postUserComment.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.postUserCommentPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Application Process Error : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.postUserCommentPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.postUserCommentPresenter.buildResponseEntity();
    }

    @PostMapping("/{commentSerialNumber}/rate")
    public ResponseEntity<String> rateComment(
            @PathVariable int commentSerialNumber,
            @RequestBody Map<String, String> request,
            HttpServletRequest httpServletRequest
    ) {
        logger.info("Endpoint : rateComment (Post) ");

        try {
            // Check user status
            AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
            FormContent formContent = FormContent.of(request);
            RateUserCommentInput input = new RateUserCommentInput.Builder()
                    .iPv4Address(servletRequest.getUserIpFromHeader())
                    .userId(servletRequest.getUserIdFromAttribute())
                    .formContent(formContent)
                    .build();
            this.rateUserComment.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.rateUserCommentPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Application Process Error : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.rateUserCommentPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.rateUserCommentPresenter.buildResponseEntity();
    }

    @PostMapping("/{commentSerialNumber}/reports")
    public ResponseEntity<String> reportComment(
            @PathVariable int commentSerialNumber,
            @RequestBody Map<String, String> request,
            HttpServletRequest httpServletRequest
    ) {
        logger.info("Endpoint : reportComment (Post) ");

        try {
            // Check user status
            AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
            FormContent formContent = FormContent.of(request);
            formContent.println("reporting : ");
            ReportUserCommentInput input = new ReportUserCommentInput.Builder()
                    .iPv4Address(servletRequest.getUserIpFromHeader())
                    .userId(servletRequest.getUserIdFromAttribute())
                    .formContent(formContent)
                    .build();
            this.reportUserComment.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.reportUserCommentPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Application Process Error : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.reportUserCommentPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.reportUserCommentPresenter.buildResponseEntity();
    }
}
