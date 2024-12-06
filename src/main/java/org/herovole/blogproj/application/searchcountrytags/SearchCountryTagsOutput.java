package org.herovole.blogproj.application.searchcountrytags;

import lombok.Builder;
import org.herovole.blogproj.domain.tag.country.CountryTagUnit;
import org.herovole.blogproj.domain.tag.country.CountryTagUnits;

@Builder
public class SearchCountryTagsOutput {
    private final CountryTagUnits tagUnits;
    private final long total;

    public Json toJsonRecord() {
        return new Json(tagUnits.toJson(), total);
    }

    record Json(CountryTagUnit.Json[] tagUnits,
                long total) {
    }
}
