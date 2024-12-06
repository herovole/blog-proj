package org.herovole.blogproj.domain.tag.country;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EmptyCountryTagUnit implements CountryTagUnit {

    @Override
    public CountryCode getCountryCode() {
        return CountryCode.empty();
    }

    @Override
    public CountryTagUnit.Json toJson() {
        return null;
    }


}
