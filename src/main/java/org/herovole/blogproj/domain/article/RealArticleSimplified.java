package org.herovole.blogproj.domain.article;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.image.ImageName;
import org.herovole.blogproj.domain.source.SourcePage;
import org.herovole.blogproj.domain.tag.country.CountryCodes;
import org.herovole.blogproj.domain.time.Timestamp;

@ToString
@Builder
@Getter
public class RealArticleSimplified implements Article {

    private final IntegerId articleId;
    private final ArticleTitle title;
    private final ArticleText text;
    private final ImageName image;
    private final SourcePage sourcePage;
    private final GenericSwitch isPublished;
    private final CountryCodes countries;
    private final IntegerIds topicTags;
    private final IntegerIds editors;
    private final int sourceComments;
    private final int userComments;
    private final Timestamp registrationTimestamp;
    private final Timestamp latestEditTimestamp;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isPublished() {
        return this.isPublished.isTrue();
    }

    @Override
    public Article append(
            IntegerIds topicTags,
            CountryCodes countries,
            IntegerIds editors,
            CommentUnits sourceComments, CommentUnits userComments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Article append(IntegerIds topicTags, CountryCodes countries, IntegerIds editors, int countSourceComments, int countUserComments) {
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
                .sourceComments(countSourceComments)
                .userComments(countUserComments)

                .registrationTimestamp(this.registrationTimestamp)
                .latestEditTimestamp(this.latestEditTimestamp)

                .build();
    }

    @Override
    public Article sortComments() {
        return this;
    }

    @Override
    public Article maskPrivateItems() {
        return this;
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
                .topicTags(topicTags.toStringMemorySignature())
                .editors(editors.toIntMemorySignature())
                .countSourceComments(sourceComments)
                .countUserComments(userComments)
                .registrationTimestamp(registrationTimestamp.letterSignatureYyyyMMddHHmmss())
                .latestEditTimestamp(registrationTimestamp.letterSignatureYyyyMMddHHmmss())
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
                String[] topicTags,
                int[] editors,
                int countSourceComments,
                int countUserComments,
                String registrationTimestamp,
                String latestEditTimestamp) implements Article.Json {
    }
}
