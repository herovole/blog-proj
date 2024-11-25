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
import org.herovole.blogproj.domain.tag.CountryCodes;
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
    public Article append(
            IntegerIds topicTags,
            CountryCodes countries,
            IntegerIds editors,
            CommentUnits sourceComments) {
        throw new UnsupportedOperationException();
    }
}
