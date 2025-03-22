package org.herovole.blogproj.domain.comment;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HandleName {

    private static final String API_KEY_HANDLE_NAME = "handleName";
    private static final String EMPTY = "-";

    private static final int LENGTH_MIN = 1;
    private static final int LENGTH_MAX = 15;
    private static final int LF_COUNT_MAX = 0;

    public static HandleName fromFormContentHandleName(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_HANDLE_NAME);
        return valueOf(child.getValue());
    }

    public static HandleName valueOf(String handleName) {
        long lfCount = handleName.chars()
                .filter(c -> c == '\n')
                .count();
        if (LF_COUNT_MAX < lfCount) {
            throw new DomainInstanceGenerationException("Too many line feeds. : " + lfCount + " / " + handleName);
        }
        if (handleName.length() < LENGTH_MIN) {
            throw new DomainInstanceGenerationException("Handle too short. : " + handleName.length() + " / " + handleName);
        }
        if (LENGTH_MAX < handleName.length()) {
            throw new DomainInstanceGenerationException("Handle too long. : " + handleName.length() + " / " + handleName);
        }
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
