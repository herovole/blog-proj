package org.herovole.blogproj.application.tag.searchcountrytags;

import lombok.Builder;
import org.herovole.blogproj.domain.tag.country.CountryTagUnit;
import org.herovole.blogproj.domain.tag.country.CountryTagUnits;

@Builder
public class SearchCountryTagsOutput {
    private final CountryTagUnits tagUnits;
    private final long total;

    public Json toJsonModel() {
        return new Json(tagUnits.toJson(), total);
    }

    public record Json(CountryTagUnit.Json[] tagUnits,
                       long total) {
    }
}
