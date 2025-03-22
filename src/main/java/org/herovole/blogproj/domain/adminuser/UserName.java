package org.herovole.blogproj.domain.adminuser;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserName {

    private static final String API_KEY_USER_NAME = "userName";
    private static final String EMPTY = "-";

    public static UserName fromFormContentUserName(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_USER_NAME);
        return valueOf(child.getValue());
    }

    public static UserName valueOf(String userName) {
        return new UserName(userName);
    }

    public static UserName empty() {
        return new UserName("");
    }

    private final String name;

    public boolean isEmpty() {
        return null == name || name.isEmpty() || EMPTY.equals(name);
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : this.name;
    }

    public String memorySignature() {
        return this.isEmpty() ? "" : this.name;
    }

}
