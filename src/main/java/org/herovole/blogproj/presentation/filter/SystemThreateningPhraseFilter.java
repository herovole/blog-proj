package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.presentation.presenter.BasicResponseBody;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.comment.TextBlackUnit;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(1)
public class SystemThreateningPhraseFilter extends OncePerRequestFilter {

    private static final Logger itsLogger = LoggerFactory.getLogger(SystemThreateningPhraseFilter.class.getSimpleName());
    private static final UseCaseErrorType FILTER_CODE = UseCaseErrorType.SYSTEM_THREATENING_PHRASE;
    private final TextBlackList textBlackList;

    @Autowired
    public SystemThreateningPhraseFilter(TextBlackList textBlackList) {
        this.textBlackList = textBlackList;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        AppServletRequest servletRequest = AppServletRequest.of(request);
        IPv4Address ip = servletRequest.getUserIpFromHeader();

        TextBlackUnit detection = servletRequest.detectThreateningPhrase(textBlackList);
        if (detection.isEmpty()) {
            filterChain.doFilter(request, response);
        }
        itsLogger.warn("IP {} attempts a malicious request with the pattern {}", ip, detection);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        BasicResponseBody errorResponseData = BasicResponseBody.builder()
                .hasPassed(false)
                .code(FILTER_CODE)
                .timestampBannedUntil(null)
                .message("One or more phrases potentially harmful to our system has been detected.")
                .build();
        response.getWriter().write(FilteringErrorResponseBody.of(errorResponseData).toJsonModel().toJsonString());
        response.getWriter().flush();
    }

}

