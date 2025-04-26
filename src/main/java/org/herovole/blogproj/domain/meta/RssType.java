package org.herovole.blogproj.domain.meta;

import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.SearchKeywords;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.article.ArticleListSearchOption;
import org.herovole.blogproj.domain.article.ArticleTransactionalDatasource;
import org.herovole.blogproj.domain.time.Date;
import org.herovole.blogproj.domain.time.RealDateRange;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum RssType {

    NONE("-") {
        @Override
        public ArticleTransactionalDatasource getCorrespondingDatasource(RssDestinations rssDestinations) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ArticleListSearchOption[] searchOptions() {
            return new ArticleListSearchOption[]{};
        }
    },
    RSS20("rss20") {
        @Override
        public ArticleTransactionalDatasource getCorrespondingDatasource(RssDestinations rssDestinations) {
            return rssDestinations.getGermanRss20Destination();
        }

        @Override
        public ArticleListSearchOption[] searchOptions() {
            return ALL_RSS_OPTIONS;
        }
    },
    RSS10("rss10") {
        @Override
        public ArticleTransactionalDatasource getCorrespondingDatasource(RssDestinations rssDestinations) {
            return rssDestinations.getGermanRss10Destination();
        }

        @Override
        public ArticleListSearchOption[] searchOptions() {
            return ALL_RSS_OPTIONS;
        }
    },
    FEED20("feed20") {
        @Override
        public ArticleTransactionalDatasource getCorrespondingDatasource(RssDestinations rssDestinations) {
            return rssDestinations.getRssFeedDestination();
        }

        @Override
        public ArticleListSearchOption[] searchOptions() {
            return ALL_RSS_OPTIONS;
        }
    },
    GERMAN20("german20") {
        @Override
        public ArticleTransactionalDatasource getCorrespondingDatasource(RssDestinations rssDestinations) {
            return rssDestinations.getGermanRss20Destination();
        }

        @Override
        public ArticleListSearchOption[] searchOptions() {
            return GERMAN_RSS_OPTIONS;
        }
    },
    GERMAN10("german10") {
        @Override
        public ArticleTransactionalDatasource getCorrespondingDatasource(RssDestinations rssDestinations) {
            return rssDestinations.getGermanRss10Destination();
        }

        @Override
        public ArticleListSearchOption[] searchOptions() {
            return GERMAN_RSS_OPTIONS;
        }
    };


    private static final Map<String, RssType> toEnum = new HashMap<>();
    private static final String API_KEY_RSSTYPE = "rssType";

    static {
        for (RssType type : values()) {
            toEnum.put(type.signature, type);
        }
    }

    public static RssType fromFormContent(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_RSSTYPE);
        return of(child.getValue());
    }

    public static RssType of(String signature) {
        return toEnum.getOrDefault(signature, NONE);
    }


    private static final ArticleListSearchOption ALL_GENRE_2WEEKS_DOZEN = ArticleListSearchOption.builder()
            .isPublished(GenericSwitch.positive())
            .pagingRequest(PagingRequest.of(1, 12))
            .keywords(SearchKeywords.empty())
            .dateRange(RealDateRange.of(Date.today().shift(-14), Date.today()))
            .build();
    private static final ArticleListSearchOption[] ALL_RSS_OPTIONS = new ArticleListSearchOption[]{ALL_GENRE_2WEEKS_DOZEN};


    private static final ArticleListSearchOption GERMAN_GENRE_HRE_4WEEKS_DOZEN = ArticleListSearchOption.builder()
            .isPublished(GenericSwitch.positive())
            .pagingRequest(PagingRequest.of(1, 12))
            .keywords(SearchKeywords.empty())
            .dateRange(RealDateRange.of(Date.today().shift(-28), Date.today()))
            .build();
    private static final ArticleListSearchOption GERMAN_GENRE_GERMANY_4WEEKS_DOZEN = ArticleListSearchOption.builder()
            .isPublished(GenericSwitch.positive())
            .pagingRequest(PagingRequest.of(1, 12))
            .keywords(SearchKeywords.empty())
            .dateRange(RealDateRange.of(Date.today().shift(-28), Date.today()))
            .build();
    private static final ArticleListSearchOption GERMAN_GENRE_AUSTRIA_4WEEKS_DOZEN = ArticleListSearchOption.builder()
            .isPublished(GenericSwitch.positive())
            .pagingRequest(PagingRequest.of(1, 12))
            .keywords(SearchKeywords.empty())
            .dateRange(RealDateRange.of(Date.today().shift(-28), Date.today()))
            .build();
    private static final ArticleListSearchOption GERMAN_GENRE_SCHWEIZ_4WEEKS_DOZEN = ArticleListSearchOption.builder()
            .isPublished(GenericSwitch.positive())
            .pagingRequest(PagingRequest.of(1, 12))
            .keywords(SearchKeywords.empty())
            .dateRange(RealDateRange.of(Date.today().shift(-28), Date.today()))
            .build();
    private static final ArticleListSearchOption GERMAN_GENRE_LIECHTENSTEIN_4WEEKS_DOZEN = ArticleListSearchOption.builder()
            .isPublished(GenericSwitch.positive())
            .pagingRequest(PagingRequest.of(1, 12))
            .keywords(SearchKeywords.empty())
            .dateRange(RealDateRange.of(Date.today().shift(-28), Date.today()))
            .build();
    private static final ArticleListSearchOption[] GERMAN_RSS_OPTIONS
            = new ArticleListSearchOption[]{GERMAN_GENRE_HRE_4WEEKS_DOZEN, GERMAN_GENRE_GERMANY_4WEEKS_DOZEN, GERMAN_GENRE_AUSTRIA_4WEEKS_DOZEN, GERMAN_GENRE_SCHWEIZ_4WEEKS_DOZEN, GERMAN_GENRE_LIECHTENSTEIN_4WEEKS_DOZEN};

    private final String signature;

    public abstract ArticleTransactionalDatasource getCorrespondingDatasource(RssDestinations rssDestinations);

    public abstract ArticleListSearchOption[] searchOptions(); // multiple elements are supposed to work as OR condition.

}
