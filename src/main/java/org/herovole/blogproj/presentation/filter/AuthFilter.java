package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.auth.validateaccesstoken.ValidateAccessToken;
import org.herovole.blogproj.application.auth.validateaccesstoken.ValidateAccessTokenInput;
import org.herovole.blogproj.application.error.AuthenticationFailureException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(5)
public class AuthFilter extends OncePerRequestFilter {

    private static final EndpointPhrases APPLIED_ENDPOINTS = EndpointPhrases.of(
            "admin"
    );
    private final ValidateAccessToken validateAccessToken;
    private final BasicPresenter presenter;

    @Autowired
    public AuthFilter(ValidateAccessToken validateAccessToken, BasicPresenter presenter) {
        this.validateAccessToken = validateAccessToken;
        this.presenter = presenter;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        AppServletRequest servletRequest = AppServletRequest.of(request);
        if (!servletRequest.hasUriContaining(APPLIED_ENDPOINTS)) {
            filterChain.doFilter(request, response);
            return;
        }
        AccessToken accessToken = servletRequest.getAccessTokenFromCookie();
        ValidateAccessTokenInput input = ValidateAccessTokenInput.builder()
                .userId(servletRequest.getUserIdFromAttribute())
                .ip(servletRequest.getUserIpFromHeader())
                .accessToken(accessToken)
                .build();
        try {
            validateAccessToken.process(input);
        } catch (AuthenticationFailureException e) {
            this.presenter.addFilteringErrorInfo(response);
            return;
        } catch (Exception e) {
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
            this.presenter.addFilteringErrorInfo(response);
            return;
        }
        filterChain.doFilter(request, response);
    }

}

