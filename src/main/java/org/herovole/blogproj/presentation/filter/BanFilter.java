package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.auth.checkuserban.CheckUserBan;
import org.herovole.blogproj.application.auth.checkuserban.CheckUserBanInput;
import org.herovole.blogproj.application.auth.checkuserban.CheckUserBanOutput;
import org.herovole.blogproj.presentation.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(4)
public class BanFilter extends OncePerRequestFilter {

    private static final String FILTER_CODE = "BAN";
    private final CheckUserBan checkUserBan;

    @Autowired
    public BanFilter(CheckUserBan checkUserBan) {
        this.checkUserBan = checkUserBan;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        ServletRequest servletRequest = ServletRequest.of(request);
        CheckUserBanInput input = CheckUserBanInput.builder()
                .userId(servletRequest.getUserIdFromAttribute())
                .iPv4Address(servletRequest.getUserIpFromHeader())
                .build();

        try {
            CheckUserBanOutput output = this.checkUserBan.process(input);
            if (!output.hasPassed()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                BlockedByFilterResponseBody errorResponseData = BlockedByFilterResponseBody.builder()
                        .code(FILTER_CODE)
                        .timestampBannedUntil(output.getTimestampBannedUntil().letterSignatureFrontendDisplay())
                        .message("You have been banned until " + output.getTimestampBannedUntil().letterSignatureYyyyMMddSpaceHHmmss())
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

