package org.herovole.blogproj.domain.article;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.OrderBy;
import org.herovole.blogproj.domain.SearchKeywords;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.tag.country.CountryCode;
import org.herovole.blogproj.domain.time.DateRange;

@ToString
@Getter
@Builder
public class ArticleListSearchOption {


    public static ArticleListSearchOption fromFormContent(FormContent formContent) {
        return ArticleListSearchOption.builder()
                .isPublished(GenericSwitch.fromFormContentIsPublished(formContent))
                .pagingRequest(PagingRequest.fromFormContent(formContent))
                .dateRange(DateRange.fromComplementedFormContent(formContent))
                .keywords(SearchKeywords.fromFormContent(formContent))
                .topic(IntegerId.fromFormContentTopicTagId(formContent))
                .country(CountryCode.fromFormContent(formContent))
                .orderBy(OrderBy.fromFormContent(formContent))
                .build();
    }

    private final GenericSwitch isPublished;
    private final PagingRequest pagingRequest;
    private final DateRange dateRange;
    private final SearchKeywords keywords;
    private final IntegerId topic;
    private final CountryCode country;
    private final OrderBy orderBy;

    public boolean isValid() {
        if (100 < this.pagingRequest.getItemsPerPage()) return false;
        if (3 < this.keywords.size()) return false;

        return true;
    }
}
