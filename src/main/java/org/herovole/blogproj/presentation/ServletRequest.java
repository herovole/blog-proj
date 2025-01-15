package org.herovole.blogproj.presentation;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.comment.TextBlackUnit;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;

import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ServletRequest {
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String COOKIE_KEY_UUID = "uuId";
    private static final String PARAM_KEY_BOT_DETECTION_TOKEN = "botDetectionToken";

    public static ServletRequest of(HttpServletRequest request) {
        return new ServletRequest(request);
    }

    private final HttpServletRequest request;

    public IPv4Address getUserIpFromHeader() {
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

    public UniversallyUniqueId getUniversalUniqueIdFromCookie() {
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

    public AccessToken getAccessTokenFromHeader() {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            return AccessToken.valueOf(token);
        }
        return AccessToken.empty();
    }

    public void storeUserIdToAttribute(IntegerPublicUserId userId) {
        request.setAttribute(ATTRIBUTE_USER_ID, userId);
    }

    public IntegerPublicUserId getUserIdFromAttribute() {
        return (IntegerPublicUserId) request.getAttribute(ATTRIBUTE_USER_ID);
    }

    public String getBotDetectionTokenFromParameter() {
        return request.getParameter(PARAM_KEY_BOT_DETECTION_TOKEN);
    }

    public TextBlackUnit detectThreateningPhrase(TextBlackList textBlackList) {
        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            TextBlackUnit detection = textBlackList.detectSystemThreat(e.getKey());
            if (!detection.isEmpty()) return detection;
            for (String v : e.getValue()) {
                textBlackList.detectSystemThreat(v);
                if (!detection.isEmpty()) return detection;
            }
        }
        return TextBlackUnit.empty();
    }

}
