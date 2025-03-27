package org.herovole.blogproj.domain.comment.importedtext;

import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.RealSourceCommentUnit;
import org.herovole.blogproj.domain.tag.country.CountryCode;
import org.herovole.blogproj.domain.tag.country.CountryCodes;
import org.herovole.blogproj.domain.tag.country.CountryTagDatasource;
import org.herovole.blogproj.domain.tag.country.CountryTagUnit;
import org.herovole.blogproj.domain.tag.country.CountryTagUnits;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdminImportedSourceCommentTest {

    static final CountryTagDatasource countryTagDatasource = new CountryTagDatasource() {
        @Override
        public CountryTagUnit findByCountryCode(CountryCode code) {
            return null;
        }

        @Override
        public CountryTagUnits search(boolean isDetailed, PagingRequest pagingRequest) {
            return null;
        }

        @Override
        public long countAll() {
            return 0;
        }

        @Override
        public CountryCodes searchCandidatesByName(String partOfCountryName) {
            return CountryCodes.of(new CountryCode[]{CountryCode.valueOf("us")});
        }
    };

    private static final String test1 = """
            #_1
            $_アメリカ
            __書き込み１行１
            書き込み１行２
            書き込み１行３__
            """;
    private static final String text = """
            書き込み１行１
            書き込み１行２
            書き込み１行３""";

    @Test
    void parse() {
        RealSourceCommentUnit unit = AdminImportedSourceComment.of(test1).buildSourceCommentUnit(countryTagDatasource);
        assertEquals(IntegerId.valueOf(1), unit.getCommentId());
        assertEquals(CountryCode.valueOf("us"), unit.getCountry());
        assertEquals(CommentText.valueOf(text), unit.getCommentText());
        assertTrue(unit.getCommentSerialNumber().isEmpty());
    }

}