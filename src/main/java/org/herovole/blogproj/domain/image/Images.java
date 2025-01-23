package org.herovole.blogproj.domain.image;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Images {
    public static Images of(Image[] items) {
        return new Images(items);
    }

    private final Image[] items;

    public Image.Json[] toJsonModel() {
        return Stream.of(this.items).map(Image::toJsonModel).toArray(Image.Json[]::new);
    }

}
