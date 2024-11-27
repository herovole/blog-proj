package org.herovole.blogproj.domain.article;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.image.ImageName;
import org.herovole.blogproj.domain.source.SourcePage;
import org.herovole.blogproj.domain.tag.CountryCodes;
import org.herovole.blogproj.domain.time.Timestamp;

@ToString
@Builder
@Getter
public class RealArticle implements Article {

    // post sample
    // key : articleEditingPage.commentEditor.1.text
    // val : This is comment4.

    static RealArticle fromPost(PostContent postContent) {
        PostContent children = postContent.getChildren(API_KEY);
        return RealArticle.builder()
                .articleId(IntegerId.fromPostContentArticleId(children))
                .title(ArticleTitle.fromPostContentArticleTitle(children))
                .text(ArticleText.fromPostContent(children))
                .image(ImageName.fromPostContentImageName(children))
                .sourcePage(SourcePage.fromPostContent(children))
                .isPublished(GenericSwitch.fromPostContentIsPublished(children))
                .countries(CountryCodes.fromPostContent(children))
                .topicTags(IntegerIds.fromPostContentTopicTags(children))
                .editors(IntegerIds.fromPostContentEditors(children))
                .originalComments(CommentUnits.fromPostContentEditors(children))

                .latestEditTimestamp(Timestamp.empty())

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
    private final Timestamp registrationTimestamp;
    private final Timestamp latestEditTimestamp;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Article append(
            IntegerIds topicTags,
            CountryCodes countries,
            IntegerIds editors,
            CommentUnits sourceComments) {
        return builder()
                .articleId(this.articleId)
                .title(this.title)
                .text(this.text)
                .image(this.image)
                .sourcePage(this.sourcePage)
                .isPublished(this.isPublished)
                .countries(countries)
                .topicTags(topicTags)
                .editors(editors)
                .originalComments(sourceComments)

                .registrationTimestamp(this.registrationTimestamp)
                .latestEditTimestamp(this.latestEditTimestamp)

                .build();
    }

    @Override
    public Article append(IntegerIds topicTags, CountryCodes countries, IntegerIds editors, int countSourceComments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Json toJsonRecord() {
        return null;
    }
}
