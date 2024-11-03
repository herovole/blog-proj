package org.herovole.blogproj.domain.tag;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;


@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TagJapanese {

    private static final String EMPTY = "-";


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
