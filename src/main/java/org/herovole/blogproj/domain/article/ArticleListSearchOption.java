package org.herovole.blogproj.domain.article;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.SearchKeywords;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.tag.country.CountryCodes;
import org.herovole.blogproj.domain.time.DateRange;

@Getter
@Builder
public class ArticleListSearchOption {


    public static ArticleListSearchOption fromFormContent(FormContent formContent) {
        return ArticleListSearchOption.builder()
                .isPublished(GenericSwitch.fromFormContentIsPublished(formContent))
                .pagingRequest(PagingRequest.fromFormContent(formContent))
                .dateRange(DateRange.fromComplementedFormContent(formContent))
                .keywords(SearchKeywords.fromFormContent(formContent))
                .topics(IntegerIds.fromFormContentTopicTags(formContent))
                .countries(CountryCodes.fromFormContent(formContent))
                .build();
    }

    private final GenericSwitch isPublished;
    private final PagingRequest pagingRequest;
    private final DateRange dateRange;
    private final SearchKeywords keywords;
    private final IntegerIds topics;
    private final CountryCodes countries;

    public boolean isValid() {
        if (100 < this.pagingRequest.getItemsPerPage()) return false;
        if (3 < this.keywords.size()) return false;
        if (3 < this.topics.size()) return false;
        if (3 < this.countries.size()) return false;

        return true;
    }
}
