package org.herovole.blogproj.application.article.searcharticles;

import lombok.*;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.article.ArticleListSearchOption;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchArticlesInput {

    public static SearchArticlesInput fromFormContent(FormContent formContent) {
        return new SearchArticlesInput(
                ArticleListSearchOption.fromFormContent(formContent),
                GenericSwitch.fromFormContentRequiresAuth(formContent)
        );
    }

    private final ArticleListSearchOption searchOption;
    private final GenericSwitch requiresAuth;
}
