package org.herovole.blogproj.domain.tag.country;

import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.tag.topic.TagUnit;
import org.herovole.blogproj.domain.tag.topic.TagUnits;

public interface CountryTagDatasource {
    CountryTagUnit findByCountryCode(CountryCode code);

    CountryTagUnits search(boolean isDetailed, PagingRequest pagingRequest);

    long countAll();
}
