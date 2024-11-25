package org.herovole.blogproj.domain.article;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.SearchKeywords;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.time.DateRange;

@Getter
@Builder
public class ArticleListSearchOption {

    private static final String API_KEY = "articleList";

    public static ArticleListSearchOption fromPostContent(PostContent postContent) {
        PostContent children = postContent.getChildren(API_KEY);
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
