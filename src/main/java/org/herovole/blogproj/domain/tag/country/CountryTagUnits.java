package org.herovole.blogproj.domain.tag.country;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CountryTagUnits {
    public static CountryTagUnits of(CountryTagUnit[] units) {
        return new CountryTagUnits(units);
    }

    private final CountryTagUnit[] units;

    public CountryTagUnit.Json[] toJson() {
        return Stream.of(this.units).map(CountryTagUnit::toJson).toArray(CountryTagUnit.Json[]::new);
    }
}
