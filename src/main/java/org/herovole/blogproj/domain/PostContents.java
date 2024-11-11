package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostContents {

    public static PostContents of(PostContent[] contents) {
        return new PostContents(contents);
    }

    private final PostContent[] contents;

    public Stream<PostContent> stream() {
        return Arrays.stream(this.contents);
    }
}
