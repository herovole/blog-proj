package org.herovole.blogproj.application.article.findarticle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FindArticleInput {

    public static FindArticleInput of(int articleIdConfirmation, FormContent formContent) {
        IntegerId articleId = IntegerId.fromFormContentArticleId(formContent);
        if (articleId.intMemorySignature() != articleIdConfirmation) {
            throw new IllegalArgumentException("Article ID mismatch");
        }
        return new FindArticleInput(
                articleId,
                GenericSwitch.fromFormContentRequiresAuth(formContent)
        );
    }

    private final IntegerId articleId;
    private final GenericSwitch requiresAuth;
}
