package org.herovole.blogproj.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ServletRequest {
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String COOKIE_KEY_UUID = "uuId";

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

    public UniversallyUniqueId getUniversalUniqueId() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_KEY_UUID.equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();
                    return UniversallyUniqueId.valueOf(cookieValue);
                }
            }
        }
        return UniversallyUniqueId.empty();
    }

    public AccessToken getAccessToken() {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            return AccessToken.valueOf(token);
        }
        return AccessToken.empty();
    }

    public void storeUserId(IntegerId userId) {
        request.setAttribute(ATTRIBUTE_USER_ID, userId);
    }

    public IntegerId getUserId() {
        return (IntegerId) request.getAttribute(ATTRIBUTE_USER_ID);
    }

}
