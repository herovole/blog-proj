package org.herovole.blogproj.application.searcharticles;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.article.ArticleListSearchOption;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchArticlesInput {

    public static SearchArticlesInput fromPostContent(PostContent postContent) {
        return new SearchArticlesInput(ArticleListSearchOption.fromPostContent(postContent));
    }

    private final ArticleListSearchOption searchOption;
}
