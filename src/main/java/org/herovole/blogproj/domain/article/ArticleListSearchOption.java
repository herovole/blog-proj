package org.herovole.blogproj.domain.article;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.SearchKeywords;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
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
                .build();
    }

    private final GenericSwitch isPublished;
    private final PagingRequest pagingRequest;
    private final DateRange dateRange;
    private final SearchKeywords keywords;

}
