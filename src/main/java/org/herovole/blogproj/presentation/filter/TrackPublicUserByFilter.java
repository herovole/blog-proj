package org.herovole.blogproj.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.application.auth.trackuser.TrackUser;
import org.herovole.blogproj.application.auth.trackuser.TrackUserInput;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.AppServletResponse;
import org.herovole.blogproj.presentation.presenter.TrackUserPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(2)
public class TrackPublicUserByFilter extends OncePerRequestFilter {

    private final TrackUser trackUser;
    private final TrackUserPresenter presenter;

    @Autowired
    public TrackPublicUserByFilter(TrackUser trackUser, TrackUserPresenter presenter) {
        this.trackUser = trackUser;
        this.presenter = presenter;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        AppServletRequest servletRequest = AppServletRequest.of(request);
        AppServletResponse servletResponse = AppServletResponse.of(response);
        TrackUserInput input = TrackUserInput.builder()
                .uuId(servletRequest.getUniversalUniqueIdFromCookie())
                .build();
        try {
            this.trackUser.process(input);
            servletRequest.storeUserIdToAttribute(this.presenter.getTrackUserOutput().getUserId());
            servletResponse.setUuIdOnCookie(this.presenter.getTrackUserOutput().getUuId());

        } catch (Exception e) {
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
            this.presenter.addFilteringErrorInfo(response);
            return;
        }
        filterChain.doFilter(request, response);
    }

}

