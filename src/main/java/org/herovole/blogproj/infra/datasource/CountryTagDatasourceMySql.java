package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.tag.country.CountryCode;
import org.herovole.blogproj.domain.tag.country.CountryCodes;
import org.herovole.blogproj.domain.tag.country.CountryTagDatasource;
import org.herovole.blogproj.domain.tag.country.CountryTagUnit;
import org.herovole.blogproj.domain.tag.country.CountryTagUnits;
import org.herovole.blogproj.infra.jpa.entity.MCountry;
import org.herovole.blogproj.infra.jpa.repository.MCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component("countryTagDatasource")
public class CountryTagDatasourceMySql implements CountryTagDatasource {

    private final MCountryRepository mCountryRepository;

    @Autowired
    public CountryTagDatasourceMySql(MCountryRepository mCountryRepository) {
        this.mCountryRepository = mCountryRepository;
    }


    @Override
    public CountryTagUnit findByCountryCode(CountryCode code) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CountryTagUnits search(boolean isDetailed, PagingRequest pagingRequest) {
        if (isDetailed) {
            throw new UnsupportedOperationException();
        } else {
            List<MCountry> mCountries = mCountryRepository.searchByOptions(pagingRequest.getLimit(), pagingRequest.getOffset());
            return CountryTagUnits.of(mCountries.stream().map(MCountry::toDomainObj).toArray(CountryTagUnit[]::new));
        }
    }

    @Override
    public long countAll() {
        return mCountryRepository.countAll();
    }

    @Override
    public CountryCodes searchCandidatesByName(String partOfCountryName) {
        List<MCountry> jpCandidates = mCountryRepository.findCandidatesByJapaneseName(partOfCountryName);
        Stream<CountryCode> jpCodes = jpCandidates.stream().map(e -> e.toDomainObj().getCountryCode());
        List<MCountry> enCandidates = mCountryRepository.findCandidatesByEnglishName(partOfCountryName);
        Stream<CountryCode> enCodes = enCandidates.stream().map(e -> e.toDomainObj().getCountryCode());
        return CountryCodes.of(Stream.concat(jpCodes, enCodes).toArray(CountryCode[]::new));
    }
}
