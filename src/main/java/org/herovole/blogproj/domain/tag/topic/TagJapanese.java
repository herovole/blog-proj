package org.herovole.blogproj.domain.tag.topic;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;


@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TagJapanese {

    private static final String EMPTY = "-";

    private static final String API_KEY_TAG_JAPANESE = "nameJp";

    public static TagJapanese fromFormContent(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_TAG_JAPANESE);
        return valueOf(child.getValue());
    }

    public static TagJapanese valueOf(String field) {
        return new TagJapanese(field);
    }

    public static TagJapanese empty() {
        return new TagJapanese(EMPTY);
    }

    private final String name;

    public boolean isEmpty() {
        return null == name || EMPTY.equals(name) || "".equals(name);
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : this.name;
    }

    public String memorySignature() {
        return this.isEmpty() ? null : this.letterSignature();
    }

}
