package org.herovole.blogproj.domain.adminuser;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessToken {

    private static final String API_KEY_ACCESS_TOKEN = "accessToken";
    private static final String EMPTY = "-";

    public static AccessToken fromFormContentAccessToken(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_ACCESS_TOKEN);
        return valueOf(child.getValue());
    }

    public static AccessToken valueOf(String accessToken) {
        return new AccessToken(accessToken);
    }

    public static AccessToken empty() {
        return new AccessToken("");
    }

    private final String token;

    public boolean isEmpty() {
        return null == token || token.isEmpty() || EMPTY.equals(token);
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : this.token;
    }

    public String memorySignature() {
        return this.isEmpty() ? null : this.token;
    }

}
