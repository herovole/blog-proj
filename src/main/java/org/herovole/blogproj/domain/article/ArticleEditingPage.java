package org.herovole.blogproj.domain.article;

import lombok.Builder;
import lombok.ToString;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.image.ImageName;
import org.herovole.blogproj.domain.source.SourcePage;
import org.herovole.blogproj.domain.tag.CountryCodes;

@ToString
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
                .title(ArticleTitle.fromPostContentArticleTitle(postContent))
                .text(ArticleText.fromPostContent(postContent))
                .image(ImageName.fromPostContentImageName(children))
                .sourcePage(SourcePage.fromPostContent(children))
                .isPublished(GenericSwitch.fromPostContentIsPublished(children))
                .countries(CountryCodes.fromPostContent(children))
                .topicTags(IntegerIds.fromPostContentTopicTags(children))
                .editors(IntegerIds.fromPostContentEditors(children))
                .originalComments(CommentUnits.fromPostContentEditors(children))
                .build();
    }

    private final IntegerId articleId;
    private final ArticleTitle title;
    private final ArticleText text;
    private final ImageName image;
    private final SourcePage sourcePage;
    private final GenericSwitch isPublished;
    private final CountryCodes countries;
    private final IntegerIds topicTags;
    private final IntegerIds editors;
    private final CommentUnits originalComments;
    private final CommentUnits userComments;
}
