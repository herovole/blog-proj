package org.herovole.blogproj.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.auth.trackuser.TrackUser;
import org.herovole.blogproj.application.auth.trackuser.TrackUserInput;
import org.herovole.blogproj.application.auth.trackuser.TrackUserOutput;
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
        TrackUserInput input = TrackUserInput.builder().uuId(servletRequest.getUniversalUniqueId()).build();
        try {
            TrackUserOutput output = this.trackUser.process(input);
            servletRequest.storeUserId(output.getUserId());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        filterChain.doFilter(request, response);
    }

}

