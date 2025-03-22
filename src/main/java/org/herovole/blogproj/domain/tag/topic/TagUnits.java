package org.herovole.blogproj.domain.tag.topic;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.FormContents;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TagUnits implements Iterable<TagUnit> {
    public static TagUnits fromFormContent(FormContent formContent) {
        FormContents formContents = formContent.getInArray();
        TagUnit[] tagUnits = formContents.stream().map(TagUnit::fromFormContent).toArray(TagUnit[]::new);
        return of(tagUnits);
    }

    public static TagUnits of(TagUnit[] units) {
        return new TagUnits(units);
    }

    private final TagUnit[] units;

    public TagUnit.Json[] toJson() {
        return Stream.of(this.units).map(TagUnit::toJson).toArray(TagUnit.Json[]::new);
    }

    @Override
    public Iterator<TagUnit> iterator() {
        return Arrays.stream(units).iterator();
    }
}
