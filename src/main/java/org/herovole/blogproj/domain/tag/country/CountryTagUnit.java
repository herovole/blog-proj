package org.herovole.blogproj.domain.tag.country;

public interface CountryTagUnit extends Comparable<CountryTagUnit> {

    static CountryTagUnit empty() {
        return new EmptyCountryTagUnit();
    }
    interface Json {
    }

    CountryCode getCountryCode();

    Json toJson();

    @Override
    default int compareTo(CountryTagUnit o) {
        return this.getCountryCode().compareTo(o.getCountryCode());
    }

}
