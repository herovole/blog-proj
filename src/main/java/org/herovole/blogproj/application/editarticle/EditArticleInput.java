package org.herovole.blogproj.application.editarticle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.article.ArticleEditingPage;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EditArticleInput {

    public static EditArticleInput fromPostContent(PostContent postContent) {
        return new EditArticleInput(ArticleEditingPage.fromPost(postContent));
    }

    private final ArticleEditingPage article;
}
