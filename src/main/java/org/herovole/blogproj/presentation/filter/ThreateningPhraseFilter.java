package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.comment.TextBlackUnit;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.presentation.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(3)
public class ThreateningPhraseFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ThreateningPhraseFilter.class.getSimpleName());
    private static final String FILTER_CODE = "THR";
    private final TextBlackList textBlackList;

    @Autowired
    public ThreateningPhraseFilter(TextBlackList textBlackList) {
        this.textBlackList = textBlackList;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        ServletRequest servletRequest = ServletRequest.of(request);
        IntegerPublicUserId userId = servletRequest.getUserIdFromAttribute();

        TextBlackUnit detection = servletRequest.detectThreateningPhrase(textBlackList);
        if (detection.isEmpty()) {
            filterChain.doFilter(request, response);
        }
        logger.warn("User {} attempts a malicious request with the pattern {}", userId, detection);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        BlockedByFilterResponseBody errorResponseData = BlockedByFilterResponseBody.builder()
                .code(FILTER_CODE)
                .timestampBannedUntil(null)
                .message("One or more phrases potentially harmful to our system has been detected.")
                .build();
        response.getWriter().write(errorResponseData.toJsonString());
        response.getWriter().flush();
    }

}

