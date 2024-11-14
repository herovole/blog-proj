package org.herovole.blogproj.domain.article;

import lombok.Builder;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.time.Date;

@Builder
public class ArticleEditingPage {

    // post sample
    // key : articleEditingPage.commentEditor.1.text
    // val : This is comment4.

    private static final String API_KEY = "articleEditingPage";

    public static ArticleEditingPage fromPost(PostContent postContent) {
        PostContent children = postContent.getChildren(API_KEY);
        return ArticleEditingPage.builder()
                .articleId(IntegerId.fromPostContentArticleId(children))
                .image(null)
                .date(Date.fromPostContentArticleDate(children))
                .isPublished(GenericSwitch.fromPostContentIsPublished(children))
                .editors(IntegerIds.fromPostContentEditors(children))
                .originalComments(CommentUnits.fromPostContentEditors(children))
                .build();
    }

    private final IntegerId articleId;
    private final Image image;
    private final Date date;
    private final GenericSwitch isPublished;
    private final IntegerIds editors;
    private final CommentUnits originalComments;
    private final CommentUnits userComments;
}
