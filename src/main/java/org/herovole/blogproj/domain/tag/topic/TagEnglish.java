package org.herovole.blogproj.domain.tag.topic;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TagEnglish {

    private static final String EMPTY = "-";


    public static TagEnglish valueOf(String field) {
        return new TagEnglish(field);
    }

    public static TagEnglish empty() {
        return new TagEnglish(EMPTY);
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
