package org.herovole.blogproj.domain.comment;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HandleName {

    private static final String API_KEY_HANDLE_NAME = "handleName";
    private static final String EMPTY = "-";

    public static HandleName fromFormContentHandleName(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_HANDLE_NAME);
        return valueOf(URLDecoder.decode(child.getValue(), StandardCharsets.UTF_8));
    }

    public static HandleName valueOf(String handleName) {
        return new HandleName(handleName);
    }

    public static HandleName empty() {
        return new HandleName("");
    }

    private final String name;

    public boolean isEmpty() {
        return null == name || name.isEmpty();
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : this.name;
    }

    public String memorySignature() {
        return this.isEmpty() ? "" : this.name;
    }

}
