package org.herovole.blogproj.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.auth.login.LoginAdmin;
import org.herovole.blogproj.application.article.editarticle.LoginAdmin;
import org.herovole.blogproj.application.article.editarticle.LoginAdminInput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
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
@RequestMapping("/api/v1/auth")
public class AdminV1AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AdminV1AuthController.class.getSimpleName());
    private static final String KEY_UUID = "uuId";
    private static final String KEY_BOT_DETECTION_TOKEN = "token";
    private final LoginAdmin loginAdmin;

    @Autowired
    AdminV1AuthController(
            LoginAdmin loginAdmin
    ) {
        this.loginAdmin = loginAdmin;
    }

    @PostMapping
    public ResponseEntity<String> login(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            @CookieValue(name = KEY_UUID, defaultValue = "") String uuId,
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : articles (Post) ");
        System.out.println(request);

        try {
            FormContent formContent = FormContent.of(request);
            LoginAdminInput input = LoginAdminInput.fromPostContent(formContent);
            this.loginAdmin.process(input);
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
