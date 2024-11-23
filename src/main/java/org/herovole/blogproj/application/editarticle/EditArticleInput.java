package org.herovole.blogproj.application.editarticle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.article.Article;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EditArticleInput {

    public static EditArticleInput fromPostContent(PostContent postContent) {
        return new EditArticleInput(Article.fromPost(postContent));
    }

    private final Article article;
}
