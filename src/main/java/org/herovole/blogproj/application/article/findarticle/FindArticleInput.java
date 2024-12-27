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

    public static FindArticleInput of(int articleId, FormContent formContent) {
        return new FindArticleInput(
                IntegerId.valueOf(articleId),
                GenericSwitch.fromFormContentIsPublished(formContent)
        );
    }

    private final IntegerId articleId;
    private final GenericSwitch isForPublic;
}
