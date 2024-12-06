package org.herovole.blogproj.application.searchcountrytags;

import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.tag.country.CountryTagDatasource;
import org.herovole.blogproj.domain.tag.country.CountryTagUnits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SearchCountryTags {

    private static final Logger logger = LoggerFactory.getLogger(SearchCountryTags.class.getSimpleName());

    private final CountryTagDatasource countryTagDatasource;

    @Autowired
    public SearchCountryTags(@Qualifier("countryTagDatasource") CountryTagDatasource countryTagDatasource) {
        this.countryTagDatasource = countryTagDatasource;
    }

    public SearchCountryTagsOutput process(SearchCountryTagsInput input) throws Exception {
        logger.info("interpreted post : {}", input);
        PagingRequest option = input.getPagingRequest();
        long total = countryTagDatasource.countAll();
        CountryTagUnits tagUnits = countryTagDatasource.search(input.isDetailed(), option);
        logger.info("job successful.");

        return SearchCountryTagsOutput.builder()
                .tagUnits(tagUnits)
                .total(total)
                .build();
    }
}
