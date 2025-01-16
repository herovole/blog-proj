package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.UseCaseErrorType;
import org.herovole.blogproj.presentation.presenter.BasicResponseBody;
import org.herovole.blogproj.application.auth.checkuserban.CheckUserBan;
import org.herovole.blogproj.application.auth.checkuserban.CheckUserBanInput;
import org.herovole.blogproj.application.auth.checkuserban.CheckUserBanOutput;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(4)
public class BanFilter extends OncePerRequestFilter {

    private static final UseCaseErrorType FILTER_CODE = UseCaseErrorType.BAN;
    private static final EndpointPhrases APPLIED_ENDPOINTS = EndpointPhrases.of(
            "usercomments"
    );
    private final CheckUserBan checkUserBan;

    @Autowired
    public BanFilter(CheckUserBan checkUserBan) {
        this.checkUserBan = checkUserBan;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        AppServletRequest servletRequest = AppServletRequest.of(request);
        if (!servletRequest.hasUriContaining(APPLIED_ENDPOINTS) || servletRequest.isGetRequest()) {
            filterChain.doFilter(request, response);
            return;
        }
        CheckUserBanInput input = CheckUserBanInput.builder()
                .userId(servletRequest.getUserIdFromAttribute())
                .iPv4Address(servletRequest.getUserIpFromHeader())
                .build();

        try {
            CheckUserBanOutput output = this.checkUserBan.process(input);
            if (!output.hasPassed()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                BasicResponseBody errorResponseData = BasicResponseBody.builder()
                        .hasPassed(false)
                        .code(FILTER_CODE)
                        .timestampBannedUntil(output.getTimestampBannedUntil())
                        .message("You have been banned until " + output.getTimestampBannedUntil().letterSignatureYyyyMMddSpaceHHmmss())
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

