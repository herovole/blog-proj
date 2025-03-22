package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.comment.TextBlackUnit;
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
@Order(2)
public class SystemThreateningPhraseFilter extends OncePerRequestFilter {

    private static final Logger itsLogger = LoggerFactory.getLogger(SystemThreateningPhraseFilter.class.getSimpleName());
    private final TextBlackList textBlackList;
    private final BasicPresenter presenter;

    @Autowired
    public SystemThreateningPhraseFilter(TextBlackList textBlackList, BasicPresenter presenter) {
        this.textBlackList = textBlackList;
        this.presenter = presenter;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        itsLogger.info("doFilterInternal");
        AppServletRequest servletRequest = AppServletRequest.of(request);
        IPv4Address ip = servletRequest.getUserIpFromHeader();

        TextBlackUnit detection = servletRequest.detectThreateningPhrase(textBlackList);
        if (detection.isEmpty()) {
            itsLogger.info("safe request");
            filterChain.doFilter(request, response);
            return;
        }
        itsLogger.warn("IP {} attempts a malicious request with the pattern {}", ip, detection);
        presenter.setUseCaseErrorType(UseCaseErrorType.SYSTEM_THREATENING_PHRASE);
        presenter.addFilteringErrorInfo(response);
    }

}

