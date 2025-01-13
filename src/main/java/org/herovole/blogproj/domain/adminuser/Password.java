package org.herovole.blogproj.domain.adminuser;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {

    private static final String API_KEY_PASSWORD = "password";
    private static final String EMPTY = "-";

    public static Password fromFormContentUserName(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_PASSWORD);
        return valueOf(child.getValue());
    }

    public static Password valueOf(String password) {
        return new Password(password);
    }

    public static Password empty() {
        return new Password("");
    }

    private final String password;

    public boolean isEmpty() {
        return null == password || password.isEmpty() || EMPTY.equals(password);
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : this.password;
    }

    public String memorySignature() {
        return this.isEmpty() ? "" : this.password;
    }

}
