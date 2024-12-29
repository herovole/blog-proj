package org.herovole.blogproj.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.user.checkuser.CheckUser;
import org.herovole.blogproj.application.user.checkuser.CheckUserInput;
import org.herovole.blogproj.application.user.checkuser.CheckUserOutput;
import org.herovole.blogproj.application.user.postusercomment.PostUserComment;
import org.herovole.blogproj.application.user.postusercomment.PostUserCommentInput;
import org.herovole.blogproj.application.user.postusercomment.PostUserCommentOutput;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicity;
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
    private final CheckUser checkUser;
    private final VerifyOrganicity verifyOrganicity;
    private final PostUserComment postUserComment;

    @Autowired
    AdminV1UserCommentController(
            CheckUser checkUser,
            VerifyOrganicity verifyOrganicity,
            PostUserComment postUserComment
    ) {
        this.checkUser = checkUser;
        this.verifyOrganicity = verifyOrganicity;
        this.postUserComment = postUserComment;
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
            CheckUserInput checkUserInput = CheckUserInput.builder()
                    .iPv4Address(servletRequest.getUserIp())
                    .uuId(UniversallyUniqueId.valueOf(uuId))
                    .build();
            CheckUserOutput checkUserOutput = this.checkUser.process(checkUserInput);
            servletResponse.setCookie(KEY_UUID, checkUserOutput.getUuId().letterSignature());
            if (!checkUserOutput.hasPassed()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Gson().toJson(checkUserOutput.toJsonModel()));
            }

            // Check if a User is not BOT
            /*
            VerifyOrganicityInput verifyOrganicityInput = VerifyOrganicityInput.builder()
                    .iPv4Address(servletRequest.getUserIp())
                    .uuId(UniversallyUniqueId.valueOf(uuId))
                    .verificationToken(request.get(KEY_BOT_DETECTION_TOKEN))
                    .build();
            VerifyOrganicityOutput verifyOrganicityOutput = this.verifyOrganicity.process(verifyOrganicityInput);
            if (!verifyOrganicityOutput.isSuccessful()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Gson().toJson(verifyOrganicityOutput.toJsonModel()));
            }
            if (!verifyOrganicityOutput.isHuman()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Gson().toJson(verifyOrganicityOutput.toJsonModel()));
            }
             */

            // Post user comment
            FormContent formContent = FormContent.of(request);
            formContent.println("comment post : ");
            PostUserCommentInput input = new PostUserCommentInput.Builder()
                    .setiPv4Address(servletRequest.getUserIp())
                    .setUserId(checkUserOutput.getUserId())
                    .setFormContent(formContent)
                    .build();
            PostUserCommentOutput output = this.postUserComment.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }

        return ResponseEntity.ok("");
    }
}
