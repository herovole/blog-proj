package org.herovole.blogproj.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.IPv4Address;
import org.springframework.http.ResponseCookie;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ServletRequest {
    public static ServletRequest of(HttpServletRequest request) {
        return new ServletRequest(request);
    }

    private final HttpServletRequest request;

    public IPv4Address getUserIp() {
        String ipAddress = request.getHeader("X-Forwarded-For"); // For reverse proxy
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getHeader("X-Real-IP"); // Alternative proxy header
        }
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr(); // Fallback to direct connection
        }

        //X-Forwarded-For often contains a list of IPs
        // (e.g., clientIP, proxy1, proxy2).
        // Use the first IP in the list as the original client IP.

        return IPv4Address.valueOf(ipAddress);
    }

    public String setCookie(HttpServletResponse response) {
        // Create a new cookie
        ResponseCookie cookie = ResponseCookie.from("myCookie", "cookieValue")
                .httpOnly(true) // Cookie can't be accessed by JavaScript
                .secure(true)   // Send over HTTPS only
                .path("/")      // Scope of the cookie
                .maxAge(7 * 24 * 60 * 60) // Expiration in seconds (7 days)
                .sameSite("Strict") // Prevent CSRF attacks
                .build();

        // Add the cookie to the response header
        response.addHeader("Set-Cookie", cookie.toString());

        return "Cookie has been set!";
    }


}
