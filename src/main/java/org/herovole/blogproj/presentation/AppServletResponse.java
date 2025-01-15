package org.herovole.blogproj.presentation;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;
import org.springframework.http.ResponseCookie;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AppServletResponse {
    public static AppServletResponse of(HttpServletResponse response) {
        return new AppServletResponse(response);
    }

    private static final String COOKIE_KEY_UUID = "uuId";
    private final HttpServletResponse response;

    public void setUuId(UniversallyUniqueId uuId) {
        this.setCookie(COOKIE_KEY_UUID, uuId.letterSignature());
    }

    private void setCookie(String key, String value) {
        // Create a new cookie
        ResponseCookie cookie = ResponseCookie.from(key, value)
                .httpOnly(true) // Cookie can't be accessed by JavaScript
                .secure(true)   // Send over HTTPS only
                .path("/")      // Scope of the cookie
                .maxAge(Long.MAX_VALUE) // Expiration in seconds
                .sameSite("Strict") // Prevent CSRF attacks
                .build();

        // Add the cookie to the response header
        response.addHeader("Set-Cookie", cookie.toString());
    }


}
