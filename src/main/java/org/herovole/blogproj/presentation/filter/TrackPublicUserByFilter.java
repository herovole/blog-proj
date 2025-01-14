package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.auth.trackuser.TrackUser;
import org.herovole.blogproj.application.auth.trackuser.TrackUserInput;
import org.herovole.blogproj.application.auth.trackuser.TrackUserOutput;
import org.herovole.blogproj.presentation.ServletRequest;
import org.herovole.blogproj.presentation.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(1)
public class TrackPublicUserByFilter extends OncePerRequestFilter {

    private final TrackUser trackUser;

    @Autowired
    public TrackPublicUserByFilter(TrackUser trackUser) {
        this.trackUser = trackUser;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        ServletRequest servletRequest = ServletRequest.of(request);
        ServletResponse servletResponse = ServletResponse.of(response);
        TrackUserInput input = TrackUserInput.builder()
                .uuId(servletRequest.getUniversalUniqueIdFromCookie())
                .build();
        try {
            TrackUserOutput output = this.trackUser.process(input);
            servletRequest.storeUserIdToAttribute(output.getUserId());
            servletResponse.setUuId(output.getUuId());
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

