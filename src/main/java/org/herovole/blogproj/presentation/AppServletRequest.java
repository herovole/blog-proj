package org.herovole.blogproj.presentation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.abstractdatasource.TextBlackList;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.herovole.blogproj.domain.comment.TextBlackUnit;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;
import org.herovole.blogproj.presentation.filter.EndpointPhrases;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AppServletRequest {
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String ATTRIBUTE_ADMIN_USER = "adminUser";
    private static final String COOKIE_KEY_UUID = "uuId";
    private static final String COOKIE_KEY_ACCESS_TOKEN = "accessToken";
    private static final String PARAM_KEY_BOT_DETECTION_TOKEN = "botDetectionToken";
    private static final String PARAM_KEY_REQUIRES_AUTH = "requiresAuth";

    public static AppServletRequest of(HttpServletRequest request) {
        return new AppServletRequest(request);
    }

    private final HttpServletRequest request;
    private JsonNode formFieldsCache;

    private GenericSwitch getRequiresAuthFromParameter() {
        return GenericSwitch.valueOf(request.getParameter(PARAM_KEY_REQUIRES_AUTH));
    }

    private GenericSwitch getRequiresAuthFromBody() {
        return GenericSwitch.valueOf(this.getValueFromBody(PARAM_KEY_REQUIRES_AUTH));
    }

    private boolean uploadsImage() {
        return request.getContentType() != null
                && request.getContentType().startsWith("multipart/");
    }

    public boolean requiresAuth() {
        return this.getRequiresAuthFromParameter().isTrue() ||
                this.getRequiresAuthFromBody().isTrue() ||
                this.uploadsImage();
    }

    public boolean hasUriContaining(EndpointPhrases endpointPhrases) {
        String requestURI = request.getRequestURI();
        return endpointPhrases.isContainedIn(requestURI);
    }

    public boolean isPostRequest() {
        return request.getMethod().toLowerCase().contains("post");
    }

    public boolean isGetRequest() {
        return request.getMethod().toLowerCase().contains("get");
    }

    public IPv4Address getUserIpFromHeader() {
        // Try to get the X-Forwarded-For header (the first IP is the client IP)
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress != null && !ipAddress.isEmpty()) {
            // If there are multiple IPs, the first one is the client IP
            String[] ipArray = ipAddress.split(",");
            ipAddress = ipArray[0].trim();
        }

        /*
        // If no X-Forwarded-For header, try the X-Real-IP header (which should be set by the reverse proxy)
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        */

        // If still no IP, fall back to the remote address of the request (proxy IP)
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }

        // You can return the IP as a String, or convert it to an IPv4Address if needed
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

    public AccessToken getAccessTokenFromCookie() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_KEY_ACCESS_TOKEN.equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();
                    return AccessToken.valueOf(cookieValue);
                }
            }
        }
        return AccessToken.empty();
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

    public void storeAdminUserToAttribute(AdminUser adminUser) {
        request.setAttribute(ATTRIBUTE_ADMIN_USER, adminUser);
    }

    public AdminUser getAdminUserFromAttribute() {
        return (AdminUser) request.getAttribute(ATTRIBUTE_ADMIN_USER);
    }

    private String getValueFromBody(String key) {
        if (this.formFieldsCache == null) {
            try (BufferedReader br = request.getReader()) {
                String body = br.lines().collect(Collectors.joining(System.lineSeparator()));

                // Parse JSON
                ObjectMapper mapper = new ObjectMapper();
                this.formFieldsCache = mapper.readTree(body);

            } catch (IOException e) {
                return null;
            }
        }
        return this.formFieldsCache.get(key) == null ? null : this.formFieldsCache.get(key).asText();
    }

    private String getBotDetectionTokenFromParameter() {
        return request.getParameter(PARAM_KEY_BOT_DETECTION_TOKEN);
    }

    private String getBotDetectionTokenFromBody() {
        return this.getValueFromBody(PARAM_KEY_BOT_DETECTION_TOKEN);
    }

    public String getBotDetectionTokenFromParameterOrBody() {
        return this.getBotDetectionTokenFromParameter() != null ?
                this.getBotDetectionTokenFromParameter() :
                this.getBotDetectionTokenFromBody();
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
