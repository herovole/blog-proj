package org.herovole.blogproj.domain.tag.topic;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TagUnits {
    public static TagUnits of(TagUnit[] units) {
        return new TagUnits(units);
    }

    private final TagUnit[] units;

    public TagUnit.Json[] toJson() {
        return Stream.of(this.units).map(TagUnit::toJson).toArray(TagUnit.Json[]::new);
    }
}
