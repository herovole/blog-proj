package org.herovole.blogproj.domain.article;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.SearchKeywords;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.time.DateRange;

@Getter
@Builder
public class ArticleListSearchOption {


    public static ArticleListSearchOption fromPostContent(FormContent formContent) {
        return ArticleListSearchOption.builder()
                .pagingRequest(PagingRequest.fromFormContent(formContent))
                .dateRange(DateRange.fromComplementedPostContent(formContent))
                .keywords(SearchKeywords.fromPostContent(formContent))
                .build();
    }

    private final PagingRequest pagingRequest;
    private final DateRange dateRange;
    private final SearchKeywords keywords;

}
