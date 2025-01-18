package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.auth.checkuserban.CheckUserBan;
import org.herovole.blogproj.application.auth.checkuserban.CheckUserBanInput;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.SuspendedUserException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(4)
public class BanFilter extends OncePerRequestFilter {

    private static final Logger itsLogger = LoggerFactory.getLogger(BanFilter.class.getSimpleName());
    private static final EndpointPhrases APPLIED_ENDPOINTS = EndpointPhrases.of(
            "usercomments"
    );
    private final CheckUserBan checkUserBan;
    private final BasicPresenter presenter;

    @Autowired
    public BanFilter(CheckUserBan checkUserBan, BasicPresenter presenter) {
        this.checkUserBan = checkUserBan;
        this.presenter = presenter;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        itsLogger.info("doFilterInternal");
        AppServletRequest servletRequest = AppServletRequest.of(request);
        if (!servletRequest.hasUriContaining(APPLIED_ENDPOINTS) || servletRequest.isGetRequest()) {
            itsLogger.info("skipped");
            filterChain.doFilter(request, response);
            return;
        }
        CheckUserBanInput input = CheckUserBanInput.builder()
                .userId(servletRequest.getUserIdFromAttribute())
                .iPv4Address(servletRequest.getUserIpFromHeader())
                .build();

        try {
            this.checkUserBan.process(input);
            itsLogger.info("not suspended");
            filterChain.doFilter(request, response);
        } catch (ApplicationProcessException e) {
            this.presenter.addFilteringErrorInfo(response);
            itsLogger.error("banned or other error", e);
        } catch (Exception e) {
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
            this.presenter.addFilteringErrorInfo(response);
            itsLogger.error("user check failure", e);
        }
    }

}

