package org.herovole.blogproj.application.editarticle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.article.Article;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EditArticleInput {

    public static EditArticleInput fromPostContent(FormContent formContent) {
        return new EditArticleInput(Article.fromPost(formContent));
    }

    private final Article article;
}
