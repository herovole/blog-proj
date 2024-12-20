package org.herovole.blogproj.application.article.findarticle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.IntegerId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FindArticleInput {

    public static FindArticleInput of(int id) {
        return new FindArticleInput(IntegerId.valueOf(id));
    }

    private final IntegerId articleId;
}
