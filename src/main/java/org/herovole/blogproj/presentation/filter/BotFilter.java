package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.presentation.presenter.BasicResponseBody;
import org.herovole.blogproj.application.auth.verifyorganicity.VerifyOrganicity;
import org.herovole.blogproj.application.auth.verifyorganicity.VerifyOrganicityInput;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(3)
public class BotFilter extends OncePerRequestFilter {

    private static final UseCaseErrorType FILTER_CODE = UseCaseErrorType.BOT;
    private static final EndpointPhrases APPLIED_ENDPOINTS = EndpointPhrases.of(
            "usercomments", "auth"
    );
    private final VerifyOrganicity verifyOrganicity;

    @Autowired
    public BotFilter(VerifyOrganicity verifyOrganicity) {
        this.verifyOrganicity = verifyOrganicity;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        AppServletRequest servletRequest = AppServletRequest.of(request);
        if (!servletRequest.hasUriContaining(APPLIED_ENDPOINTS) || servletRequest.isGetRequest()) {
            filterChain.doFilter(request, response);
            return;
        }

        VerifyOrganicityInput input = VerifyOrganicityInput.builder()
                .userId(servletRequest.getUserIdFromAttribute())
                .iPv4Address(servletRequest.getUserIpFromHeader())
                .verificationToken(servletRequest.getBotDetectionTokenFromParameter())
                .build();
        try {
            VerifyOrganicityOutput output = verifyOrganicity.process(input);
            if (!output.isHuman()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                BasicResponseBody errorResponseData = BasicResponseBody.builder()
                        .hasPassed(false)
                        .code(FILTER_CODE)
                        .timestampBannedUntil(null)
                        .message("Potential Bot Request.")
                        .build();
                response.getWriter().write(FilteringErrorResponseBody.of(errorResponseData).toJsonModel().toJsonString());
                response.getWriter().flush();
                return;
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(FilteringErrorResponseBody.internalServerError().toJsonModel().toJsonString());
            response.getWriter().flush();
            return;
        }
        filterChain.doFilter(request, response);
    }

}

