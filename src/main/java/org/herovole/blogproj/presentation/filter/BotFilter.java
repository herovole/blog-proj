package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.auth.verifyorganicity.VerifyOrganicity;
import org.herovole.blogproj.application.auth.verifyorganicity.VerifyOrganicityInput;
import org.herovole.blogproj.application.error.BotDetectionException;
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
@Order(3)
public class BotFilter extends OncePerRequestFilter {

    private static final Logger itsLogger = LoggerFactory.getLogger(BotFilter.class.getSimpleName());
    private static final EndpointPhrases APPLIED_ENDPOINTS = EndpointPhrases.of(
            "usercomments", "auth"
    );
    private final VerifyOrganicity verifyOrganicity;
    private final BasicPresenter presenter;

    @Autowired
    public BotFilter(VerifyOrganicity verifyOrganicity, BasicPresenter presenter) {
        this.verifyOrganicity = verifyOrganicity;
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

        VerifyOrganicityInput input = VerifyOrganicityInput.builder()
                .userId(servletRequest.getUserIdFromAttribute())
                .iPv4Address(servletRequest.getUserIpFromHeader())
                .verificationToken(servletRequest.getBotDetectionTokenFromParameter())
                .build();
        try {
            verifyOrganicity.process(input);
            itsLogger.info("is human");
            filterChain.doFilter(request, response);
        } catch (BotDetectionException e) {
            this.presenter.addFilteringErrorInfo(response);
            itsLogger.error("is bot", e);
        } catch (Exception e) {
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
            this.presenter.addFilteringErrorInfo(response);
            itsLogger.error("verification failed", e);
        }
    }

}

