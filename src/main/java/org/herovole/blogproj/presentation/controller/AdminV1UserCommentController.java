package org.herovole.blogproj.presentation.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.herovole.blogproj.application.user.postusercomment.PostUserComment;
import org.herovole.blogproj.application.user.postusercomment.PostUserCommentInput;
import org.herovole.blogproj.application.user.postusercomment.PostUserCommentOutput;
import org.herovole.blogproj.application.user.rateusercomment.RateUserComment;
import org.herovole.blogproj.application.user.rateusercomment.RateUserCommentInput;
import org.herovole.blogproj.application.user.rateusercomment.RateUserCommentOutput;
import org.herovole.blogproj.application.user.reportusercomment.ReportUserComment;
import org.herovole.blogproj.application.user.reportusercomment.ReportUserCommentInput;
import org.herovole.blogproj.application.user.reportusercomment.ReportUserCommentOutput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.presentation.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final RateUserComment rateUserComment;
    private final ReportUserComment reportUserComment;

    @Autowired
    AdminV1UserCommentController(
            PostUserComment postUserComment,
            RateUserComment rateUserComment,
            ReportUserComment reportUserComment
    ) {
        this.postUserComment = postUserComment;
        this.rateUserComment = rateUserComment;
        this.reportUserComment = reportUserComment;
    }

    @PostMapping
    public ResponseEntity<String> postComment(
            @RequestBody Map<String, String> request,
            HttpServletRequest httpServletRequest
            ) {
        logger.info("Endpoint : userComments (Post) ");

        try {
            // Check user status
            ServletRequest servletRequest = ServletRequest.of(httpServletRequest);
            FormContent formContent = FormContent.of(request);
            PostUserCommentInput input = new PostUserCommentInput.Builder()
                    .iPv4Address(servletRequest.getUserIpFromHeader())
                    .userId(servletRequest.getUserIdFromAttribute())
                    .formContent(formContent)
                    .build();

            logger.info("postComment input : {}", input);
            PostUserCommentOutput output = this.postUserComment.process(input);
            return ResponseEntity.ok(new Gson().toJson(output.toJsonModel()));
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
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
            ServletRequest servletRequest = ServletRequest.of(httpServletRequest);
            FormContent formContent = FormContent.of(request);
            RateUserCommentInput input = new RateUserCommentInput.Builder()
                    .iPv4Address(servletRequest.getUserIpFromHeader())
                    .userId(servletRequest.getUserIdFromAttribute())
                    .formContent(formContent)
                    .build();
            RateUserCommentOutput output = this.rateUserComment.process(input);
            return ResponseEntity.ok(new Gson().toJson(output.toJsonModel()));
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
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
            ServletRequest servletRequest = ServletRequest.of(httpServletRequest);
            FormContent formContent = FormContent.of(request);
            formContent.println("reporting : ");
            ReportUserCommentInput input = new ReportUserCommentInput.Builder()
                    .iPv4Address(servletRequest.getUserIpFromHeader())
                    .userId(servletRequest.getUserIdFromAttribute())
                    .formContent(formContent)
                    .build();
            ReportUserCommentOutput output = this.reportUserComment.process(input);
            return ResponseEntity.ok(new Gson().toJson(output.toJsonModel()));
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
}
