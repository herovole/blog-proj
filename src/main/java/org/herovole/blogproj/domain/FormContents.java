package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FormContents {

    public static FormContents of(FormContent[] contents) {
        return new FormContents(contents);
    }

    private final FormContent[] contents;

    public Stream<FormContent> stream() {
        return Arrays.stream(this.contents);
    }

}
