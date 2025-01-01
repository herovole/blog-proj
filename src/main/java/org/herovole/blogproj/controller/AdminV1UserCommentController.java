package org.herovole.blogproj.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.user.postusercomment.ProcessPostUserComment;
import org.herovole.blogproj.application.user.postusercomment.ProcessPostUserCommentInput;
import org.herovole.blogproj.application.user.postusercomment.ProcessPostUserCommentOutput;
import org.herovole.blogproj.application.user.rateusercomment.ProcessRateUserComment;
import org.herovole.blogproj.application.user.rateusercomment.ProcessRateUserCommentInput;
import org.herovole.blogproj.application.user.rateusercomment.ProcessRateUserCommentOutput;
import org.herovole.blogproj.application.user.reportusercomment.ProcessReportUserComment;
import org.herovole.blogproj.application.user.reportusercomment.ProcessReportUserCommentInput;
import org.herovole.blogproj.application.user.reportusercomment.ProcessReportUserCommentOutput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.user.UniversallyUniqueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/usercomments")
public class AdminV1UserCommentController {
    private static final Logger logger = LoggerFactory.getLogger(AdminV1UserCommentController.class.getSimpleName());

    private static final String KEY_UUID = "uuId";
    private static final String KEY_BOT_DETECTION_TOKEN = "token";
    private final ProcessPostUserComment processPostUserComment;
    private final ProcessRateUserComment processRateUserComment;
    private final ProcessReportUserComment processReportUserComment;

    @Autowired
    AdminV1UserCommentController(
            ProcessPostUserComment processPostUserComment,
            ProcessRateUserComment processRateUserComment,
            ProcessReportUserComment processReportUserComment
    ) {
        this.processPostUserComment = processPostUserComment;
        this.processRateUserComment = processRateUserComment;
        this.processReportUserComment = processReportUserComment;
    }

    @PostMapping
    public ResponseEntity<String> postComment(
            @RequestBody Map<String, String> request,
            @CookieValue(name = KEY_UUID, defaultValue = "") String uuId,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        logger.info("Endpoint : userComments (Post) ");

        try {
            // Check user status
            ServletRequest servletRequest = ServletRequest.of(httpServletRequest);
            ServletResponse servletResponse = ServletResponse.of(httpServletResponse);
            FormContent formContent = FormContent.of(request);
            ProcessPostUserCommentInput input = new ProcessPostUserCommentInput.Builder()
                    .setiPv4Address(servletRequest.getUserIp())
                    .setUuId(UniversallyUniqueId.valueOf(uuId))
                    .setVerificationToken(request.get(KEY_BOT_DETECTION_TOKEN))
                    .setFormContent(formContent)
                    .build();
            ProcessPostUserCommentOutput output = this.processPostUserComment.process(input);
            servletResponse.setCookie(KEY_UUID, output.getUuId().letterSignature());
            return ResponseEntity.ok(new Gson().toJson(output.toJsonModel()));
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/rate")
    public ResponseEntity<String> rateComment(
            @RequestBody Map<String, String> request,
            @CookieValue(name = KEY_UUID, defaultValue = "") String uuId,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        logger.info("Endpoint : rateComment (Post) ");

        try {
            // Check user status
            ServletRequest servletRequest = ServletRequest.of(httpServletRequest);
            ServletResponse servletResponse = ServletResponse.of(httpServletResponse);
            FormContent formContent = FormContent.of(request);
            ProcessRateUserCommentInput input = new ProcessRateUserCommentInput.Builder()
                    .setiPv4Address(servletRequest.getUserIp())
                    .setUuId(UniversallyUniqueId.valueOf(uuId))
                    .setVerificationToken(request.get(KEY_BOT_DETECTION_TOKEN))
                    .setFormContent(formContent)
                    .build();
            ProcessRateUserCommentOutput output = this.processRateUserComment.process(input);
            servletResponse.setCookie(KEY_UUID, output.getUuId().letterSignature());
            return ResponseEntity.ok(new Gson().toJson(output.toJsonModel()));
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/report")
    public ResponseEntity<String> reportComment(
            @RequestBody Map<String, String> request,
            @CookieValue(name = KEY_UUID, defaultValue = "") String uuId,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        logger.info("Endpoint : reportComment (Post) ");

        try {
            // Check user status
            ServletRequest servletRequest = ServletRequest.of(httpServletRequest);
            ServletResponse servletResponse = ServletResponse.of(httpServletResponse);
            FormContent formContent = FormContent.of(request);
            ProcessReportUserCommentInput input = new ProcessReportUserCommentInput.Builder()
                    .setiPv4Address(servletRequest.getUserIp())
                    .setUuId(UniversallyUniqueId.valueOf(uuId))
                    .setVerificationToken(request.get(KEY_BOT_DETECTION_TOKEN))
                    .setFormContent(formContent)
                    .build();
            ProcessReportUserCommentOutput output = this.processReportUserComment.process(input);
            servletResponse.setCookie(KEY_UUID, output.getUuId().letterSignature());
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
