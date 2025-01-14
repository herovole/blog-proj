package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicity;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicityInput;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicityOutput;
import org.herovole.blogproj.presentation.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(2)
public class BotFilter extends OncePerRequestFilter {

    private static final String FILTER_CODE = "BOT";
    private final VerifyOrganicity verifyOrganicity;

    @Autowired
    public BotFilter(VerifyOrganicity verifyOrganicity) {
        this.verifyOrganicity = verifyOrganicity;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        ServletRequest servletRequest = ServletRequest.of(request);
        VerifyOrganicityInput input = VerifyOrganicityInput.builder()
                .userId(servletRequest.getUserIdFromAttribute())
                .iPv4Address(servletRequest.getUserIpFromHeader())
                .verificationToken(servletRequest.getBotDetectionTokenFromParameter())
                .build();
        try {
            VerifyOrganicityOutput output = verifyOrganicity.process(input);
            if (!output.isHuman()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                BlockedByFilterResponseBody errorResponseData = BlockedByFilterResponseBody.builder()
                        .code(FILTER_CODE)
                        .timestampBannedUntil(null)
                        .message("Potential Bot Request.")
                        .build();
                response.getWriter().write(errorResponseData.toJsonString());
                response.getWriter().flush();
                return;
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BlockedByFilterResponseBody errorResponseData = BlockedByFilterResponseBody.builder()
                    .code(BlockedByFilterResponseBody.FILTER_CODE_SERVER_ERROR)
                    .timestampBannedUntil(null)
                    .message("Internal Server Error.")
                    .build();
            response.getWriter().write(errorResponseData.toJsonString());
            response.getWriter().flush();
            return;
        }
        filterChain.doFilter(request, response);
    }

}

