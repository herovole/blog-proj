package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.auth.trackuser.TrackUser;
import org.herovole.blogproj.application.auth.trackuser.TrackUserInput;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.AppServletResponse;
import org.herovole.blogproj.presentation.presenter.TrackUserPresenter;
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
public class TrackPublicUserByFilter extends OncePerRequestFilter {

    private static final Logger itsLogger = LoggerFactory.getLogger(TrackPublicUserByFilter.class.getSimpleName());
    private final TrackUser trackUser;
    private final TrackUserPresenter presenter;

    @Autowired
    public TrackPublicUserByFilter(TrackUser trackUser, TrackUserPresenter presenter) {
        this.trackUser = trackUser;
        this.presenter = presenter;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        itsLogger.info("doFilterInternal");
        AppServletRequest servletRequest = AppServletRequest.of(request);
        AppServletResponse servletResponse = AppServletResponse.of(response);
        TrackUserInput input = TrackUserInput.builder()
                .uuId(servletRequest.getUniversalUniqueIdFromCookie())
                .build();
        try {
            this.trackUser.process(input);
            servletRequest.storeUserIdToAttribute(this.presenter.getContent().getUserId());
            servletResponse.setUuIdOnCookie(this.presenter.getContent().getUuId());
            itsLogger.info("process successful");
            filterChain.doFilter(request, response);

        } catch (ApplicationProcessException e) {
            itsLogger.error("application process error");
            this.presenter.addFilteringErrorInfo(response);
        } catch (Exception e) {
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
            this.presenter.addFilteringErrorInfo(response);
            itsLogger.error("tracking failure", e);
        }
    }

}

