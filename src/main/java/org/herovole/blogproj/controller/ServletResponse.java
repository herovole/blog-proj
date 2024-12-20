package org.herovole.blogproj.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ServletResponse {
    public static ServletResponse of(HttpServletResponse response) {
        return new ServletResponse(response);
    }

    private final HttpServletResponse response;

    public void setCookie(String key, String value) {
        // Create a new cookie
        ResponseCookie cookie = ResponseCookie.from(key, value)
                .httpOnly(true) // Cookie can't be accessed by JavaScript
                .secure(true)   // Send over HTTPS only
                .path("/")      // Scope of the cookie
                .maxAge(7 * 24 * 60 * 60) // Expiration in seconds (7 days)
                .sameSite("Strict") // Prevent CSRF attacks
                .build();

        // Add the cookie to the response header
        response.addHeader("Set-Cookie", cookie.toString());
    }


}
