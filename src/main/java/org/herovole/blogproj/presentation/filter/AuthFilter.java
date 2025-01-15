package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.adminuser.AccessTokenFactory;
import org.herovole.blogproj.presentation.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(5)
public class AuthFilter extends OncePerRequestFilter {

    private static final String FILTER_CODE = "AUT";
    private final AccessTokenFactory accessTokenFactory;

    @Autowired
    public AuthFilter(AccessTokenFactory accessTokenFactory) {
        this.accessTokenFactory = accessTokenFactory;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        ServletRequest servletRequest = ServletRequest.of(request);
        AccessToken accessToken = servletRequest.getAccessTokenFromHeader();
        try {
            accessTokenFactory.validateToken(accessToken);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            BlockedByFilterResponseBody errorResponseData = BlockedByFilterResponseBody.builder()
                    .code(FILTER_CODE)
                    .timestampBannedUntil(null)
                    .message("Valid token is absent.")
                    .build();
            response.getWriter().write(errorResponseData.toJsonString());
            response.getWriter().flush();
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI().toLowerCase();
        return !path.contains("admin");
    }
}

