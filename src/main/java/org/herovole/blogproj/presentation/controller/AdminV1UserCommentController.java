package org.herovole.blogproj.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.application.user.handlereport.HandleReport;
import org.herovole.blogproj.application.user.handlereport.HandleReportInput;
import org.herovole.blogproj.application.user.hidecomment.HideUserComment;
import org.herovole.blogproj.application.user.hidecomment.HideUserCommentInput;
import org.herovole.blogproj.application.user.postusercomment.PostUserComment;
import org.herovole.blogproj.application.user.postusercomment.PostUserCommentInput;
import org.herovole.blogproj.application.user.rateusercomment.RateUserComment;
import org.herovole.blogproj.application.user.rateusercomment.RateUserCommentInput;
import org.herovole.blogproj.application.user.reportusercomment.ReportUserComment;
import org.herovole.blogproj.application.user.reportusercomment.ReportUserCommentInput;
import org.herovole.blogproj.application.user.searchcomments.SearchUserComments;
import org.herovole.blogproj.application.user.searchcomments.SearchUserCommentsInput;
import org.herovole.blogproj.application.user.searchratinghistory.SearchRatingHistory;
import org.herovole.blogproj.application.user.searchratinghistory.SearchRatingHistoryInput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.herovole.blogproj.presentation.presenter.SearchRatingHistoryPresenter;
import org.herovole.blogproj.presentation.presenter.SearchUserCommentsPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/usercomments")
public class AdminV1UserCommentController {
    private static final Logger logger = LoggerFactory.getLogger(AdminV1UserCommentController.class.getSimpleName());

    private final PostUserComment postUserComment;
    private final BasicPresenter postUserCommentPresenter;
    private final SearchRatingHistory searchRatingHistory;
    private final SearchRatingHistoryPresenter searchRatingHistoryPresenter;
    private final RateUserComment rateUserComment;
    private final BasicPresenter rateUserCommentPresenter;
    private final ReportUserComment reportUserComment;
    private final BasicPresenter reportUserCommentPresenter;
    private final SearchUserComments searchUserComments;
    private final SearchUserCommentsPresenter searchUserCommentsPresenter;
    private final HideUserComment hideUserComment;
    private final BasicPresenter hideUserCommentPresenter;
    private final HandleReport handleReport;
    private final BasicPresenter handleReportPresenter;

    @Autowired
    AdminV1UserCommentController(
            PostUserComment postUserComment, BasicPresenter postUserCommentPresenter,
            SearchRatingHistory searchRatingHistory, SearchRatingHistoryPresenter searchRatingHistoryPresenter,
            RateUserComment rateUserComment, BasicPresenter rateUserCommentPresenter,
            ReportUserComment reportUserComment, BasicPresenter reportUserCommentPresenter, SearchUserComments searchUserComments, SearchUserCommentsPresenter searchUserCommentsPresenter, HideUserComment hideUserComment, BasicPresenter hideUserCommentPresenter, HandleReport handleReport, BasicPresenter handleReportPresenter) {
        this.postUserComment = postUserComment;
        this.postUserCommentPresenter = postUserCommentPresenter;
        this.searchRatingHistory = searchRatingHistory;
        this.searchRatingHistoryPresenter = searchRatingHistoryPresenter;
        this.rateUserComment = rateUserComment;
        this.rateUserCommentPresenter = rateUserCommentPresenter;
        this.reportUserComment = reportUserComment;
        this.reportUserCommentPresenter = reportUserCommentPresenter;
        this.searchUserComments = searchUserComments;
        this.searchUserCommentsPresenter = searchUserCommentsPresenter;
        this.hideUserComment = hideUserComment;
        this.hideUserCommentPresenter = hideUserCommentPresenter;
        this.handleReport = handleReport;
        this.handleReportPresenter = handleReportPresenter;
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

    @GetMapping
    public ResponseEntity<String> searchUserComments(
            @RequestParam Map<String, String> request,
            HttpServletRequest httpServletRequest
    ) {
        logger.info("Endpoint : usercomments (Get) ");
        System.out.println(request);
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        if (!servletRequest.getAdminUserFromAttribute().getRole().hasAccessToAdmin()) {
            this.searchUserCommentsPresenter.setUseCaseErrorType(UseCaseErrorType.AUTH_INSUFFICIENT);
            return this.searchUserCommentsPresenter.buildResponseEntity();
        }
        try {
            FormContent formContent = FormContent.of(request);
            SearchUserCommentsInput input = SearchUserCommentsInput.of(formContent);
            this.searchUserComments.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.searchUserCommentsPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.searchUserCommentsPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.searchUserCommentsPresenter.buildResponseEntity();
    }

    @GetMapping("/ratings")
    public ResponseEntity<String> searchRatingHistory(
            @RequestParam Map<String, String> request,
            HttpServletRequest httpServletRequest
    ) {
        logger.info("Endpoint : usercomments/ratings (Get) ");
        System.out.println(request);
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        try {
            FormContent formContent = FormContent.of(request);
            SearchRatingHistoryInput input = new SearchRatingHistoryInput.Builder()
                    .iPv4Address(servletRequest.getUserIpFromHeader())
                    .userId(servletRequest.getUserIdFromAttribute())
                    .formContent(formContent)
                    .build();
            this.searchRatingHistory.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.searchRatingHistoryPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.searchRatingHistoryPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.searchRatingHistoryPresenter.buildResponseEntity();
    }

    @PostMapping("/{commentSerialNumber}/rate")
    public ResponseEntity<String> rateComment(
            @PathVariable("commentSerialNumber") long commentSerialNumber,
            @RequestBody Map<String, String> request,
            HttpServletRequest httpServletRequest
    ) {
        logger.info("Endpoint : rateComment (Post) ");

        try {
            // Check user status
            AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
            FormContent formContent = FormContent.of(request);
            RateUserCommentInput input = new RateUserCommentInput.Builder()
                    .commentSerialNumberConfirmation(commentSerialNumber)
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
            @PathVariable("commentSerialNumber") long commentSerialNumber,
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
                    .commentSerialNumberConfirmation(commentSerialNumber)
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

    @PutMapping("/{commentSerialNumber}/hide")
    public ResponseEntity<String> hideComment(
            @PathVariable long commentSerialNumber,
            @RequestBody Map<String, String> request,
            HttpServletRequest httpServletRequest
    ) {
        logger.info("Endpoint : reportComment (Put) ");

        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        if (!servletRequest.getAdminUserFromAttribute().getRole().bansUsers()) {
            this.hideUserCommentPresenter.setUseCaseErrorType(UseCaseErrorType.AUTH_INSUFFICIENT);
            return this.hideUserCommentPresenter.buildResponseEntity();
        }
        FormContent formContent = FormContent.of(request);
        formContent.println("reporting : ");
        try {
            HideUserCommentInput input = HideUserCommentInput.ofFormContent(commentSerialNumber, formContent);
            this.hideUserComment.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.hideUserCommentPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Application Process Error : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.hideUserCommentPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.hideUserCommentPresenter.buildResponseEntity();
    }

    @PutMapping("/reports/{reportId}/handle")
    public ResponseEntity<String> handleReport(
            @PathVariable long reportId,
            @RequestBody Map<String, String> request,
            HttpServletRequest httpServletRequest
    ) {
        logger.info("Endpoint : handle report (Put) ");

        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        if (!servletRequest.getAdminUserFromAttribute().getRole().bansUsers()) {
            this.hideUserCommentPresenter.setUseCaseErrorType(UseCaseErrorType.AUTH_INSUFFICIENT);
            return this.hideUserCommentPresenter.buildResponseEntity();
        }
        FormContent formContent = FormContent.of(request);
        formContent.println("reporting : ");
        try {
            HandleReportInput input = HandleReportInput.ofFormContent(reportId, formContent);
            this.handleReport.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.handleReportPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Application Process Error : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.handleReportPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.handleReportPresenter.buildResponseEntity();
    }
}
