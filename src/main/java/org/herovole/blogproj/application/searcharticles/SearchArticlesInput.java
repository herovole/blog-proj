package org.herovole.blogproj.application.searcharticles;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.article.ArticleListSearchOption;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchArticlesInput {


    public static SearchArticlesInput fromPostContent(FormContent formContent) {
        return new SearchArticlesInput(ArticleListSearchOption.fromPostContent(formContent));
    }

    private final ArticleListSearchOption searchOption;
}
