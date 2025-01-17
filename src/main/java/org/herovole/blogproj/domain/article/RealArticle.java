package org.herovole.blogproj.domain.article;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.image.ImageName;
import org.herovole.blogproj.domain.source.SourcePage;
import org.herovole.blogproj.domain.tag.country.CountryCodes;
import org.herovole.blogproj.domain.time.Timestamp;

@ToString
@Builder
@Getter
public class RealArticle implements Article {

    // post sample
    // key : articleEditingPage.commentEditor.1.text
    // val : This is comment4.

    static RealArticle fromPost(FormContent formContent) {
        FormContent children = formContent.getChildren(API_KEY);
        return RealArticle.builder()
                .articleId(IntegerId.fromFormContentArticleId(children))
                .title(ArticleTitle.fromPostContentArticleTitle(children))
                .text(ArticleText.fromPostContent(children))
                .image(ImageName.fromPostContentImageName(children))
                .sourcePage(SourcePage.fromPostContent(children))
                .isPublished(GenericSwitch.fromFormContentIsPublished(children))
                .countries(CountryCodes.fromPostContent(children))
                .topicTags(IntegerIds.fromPostContentTopicTags(children))
                .editors(IntegerIds.fromPostContentEditors(children))
                .originalComments(CommentUnits.fromFormContentToSourceComments(children))
                .userComments(CommentUnits.fromFormContentToUserComments(children))

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
            CommentUnits sourceComments,
            CommentUnits userComments) {
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
                .userComments(userComments)

                .registrationTimestamp(this.registrationTimestamp)
                .latestEditTimestamp(this.latestEditTimestamp)

                .build();
    }

    @Override
    public Article append(IntegerIds topicTags,
                          CountryCodes countries,
                          IntegerIds editors,
                          int countSourceComments,
                          int countUserComments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Article sortComments() {
        return this.append(this.topicTags, this.countries, this.editors, this.originalComments.sort(), this.userComments.sort());
    }

    @Override
    public Article maskPrivateItems() {
        return builder()
                .articleId(this.articleId)
                .title(this.title)
                .text(this.text)
                .image(this.image)
                .sourcePage(this.sourcePage)
                .isPublished(this.isPublished)
                .countries(this.countries)
                .topicTags(this.topicTags)
                .editors(this.editors)
                .originalComments(this.originalComments)
                .userComments(this.userComments.maskPrivateItems())
                .registrationTimestamp(this.registrationTimestamp)
                .latestEditTimestamp(this.latestEditTimestamp)
                .build();
    }

    @Override
    public Article filterOutHiddenComments() {
        return builder()
                .articleId(this.articleId)
                .title(this.title)
                .text(this.text)
                .image(this.image)
                .sourcePage(this.sourcePage)
                .isPublished(this.isPublished)
                .countries(this.countries)
                .topicTags(this.topicTags)
                .editors(this.editors)
                .originalComments(this.originalComments.filterOutHiddenComments())
                .userComments(this.userComments.filterOutHiddenComments())
                .registrationTimestamp(this.registrationTimestamp)
                .latestEditTimestamp(this.latestEditTimestamp)
                .build();
    }

    @Override
    public Json toJsonModel() {
        return Json.builder()
                .articleId(articleId.longMemorySignature())
                .title(title.memorySignature())
                .text(text.memorySignature())
                .imageName(image.memorySignature())
                .sourceUrl(sourcePage.getUrl().memorySignature())
                .sourceTitle(sourcePage.getTitle().memorySignature())
                .sourceDate(sourcePage.getDate().letterSignature())
                .isPublished(isPublished.memorySignature())
                .countries(countries.toMemorySignature())
                .topicTags(topicTags.toIntMemorySignature())
                .editors(editors.toIntMemorySignature())
                .sourceComments(originalComments.toJson())
                .userComments(userComments.toJson())
                .registrationTimestamp(registrationTimestamp.letterSignatureYyyyMMddSpaceHHmmss())
                .latestEditTimestamp(registrationTimestamp.letterSignatureYyyyMMddSpaceHHmmss())
                .build();
    }

    @Builder
    record Json(long articleId,
                String title,
                String text,
                String imageName,
                String sourceUrl,
                String sourceTitle,
                String sourceDate,
                Boolean isPublished,
                String[] countries,
                int[] topicTags,
                int[] editors,
                CommentUnit.Json[] sourceComments,
                CommentUnit.Json[] userComments,
                String registrationTimestamp,
                String latestEditTimestamp) implements Article.Json {
    }
}
