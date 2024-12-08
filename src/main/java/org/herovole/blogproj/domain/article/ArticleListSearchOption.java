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

    private static final String API_KEY = "articleList";

    public static ArticleListSearchOption fromPostContent(FormContent formContent) {
        FormContent children = formContent.getChildren(API_KEY);
        children.println("articleListSearchOption");
        return ArticleListSearchOption.builder()
                .pagingRequest(PagingRequest.fromPostContent(children))
                .dateRange(DateRange.fromComplementedPostContent(children))
                .keywords(SearchKeywords.fromPostContent(children))
                .build();
    }

    private final PagingRequest pagingRequest;
    private final DateRange dateRange;
    private final SearchKeywords keywords;

}
