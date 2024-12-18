package org.herovole.blogproj.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.herovole.blogproj.application.postusercomment.PostUserComment;
import org.herovole.blogproj.application.postusercomment.PostUserCommentInput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    AdminV1UserCommentController(
            PostUserComment postUserComment
    ) {
        this.postUserComment = postUserComment;
    }

    @PostMapping
    public ResponseEntity<String> postComment(
            @RequestBody Map<String, String> request,
            HttpServletRequest servletRequest
    ) {
        logger.info("Endpoint : userComments (Post) ");
        System.out.println(request);

        try {
            FormContent formContent = FormContent.of(request);
            PostUserCommentInput input = PostUserCommentInput.fromFormContent(formContent);
            this.postUserComment.process(input);
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
