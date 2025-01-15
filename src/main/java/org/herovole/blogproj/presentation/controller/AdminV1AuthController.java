package org.herovole.blogproj.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.auth.login.LoginAdmin;
import org.herovole.blogproj.application.auth.login.LoginAdminInput;
import org.herovole.blogproj.application.auth.login.LoginAdminOutput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.presentation.AppServletRequest;
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
@RequestMapping("/api/v1/auth")
public class AdminV1AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AdminV1AuthController.class.getSimpleName());
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
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : articles (Post) ");
        System.out.println(request);
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);

        try {
            FormContent formContent = FormContent.of(request);
            LoginAdminInput input = LoginAdminInput.of(servletRequest.getUserIpFromHeader(), formContent);
            LoginAdminOutput output = this.loginAdmin.process(input);
            return ResponseEntity.ok(output.toJsonModel().toJsonString());
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }

    }

}
