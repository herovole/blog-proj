package org.herovole.blogproj.application.searcharticles;

import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.article.Article;
import org.herovole.blogproj.domain.article.ArticleText;
import org.herovole.blogproj.domain.article.ArticleTitle;
import org.herovole.blogproj.domain.article.RealArticleSimplified;
import org.herovole.blogproj.domain.image.ImageName;
import org.herovole.blogproj.domain.source.SourcePage;
import org.herovole.blogproj.domain.source.SourceUrl;
import org.herovole.blogproj.domain.tag.country.CountryCode;
import org.herovole.blogproj.domain.tag.country.CountryCodes;
import org.herovole.blogproj.domain.time.Date;
import org.herovole.blogproj.domain.time.Timestamp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class SearchArticlesOutputTest {

    private final Article article = RealArticleSimplified.builder()
            .articleId(IntegerId.valueOf(10))
            .image(ImageName.valueOf("imageName.jpg"))
            .text(ArticleText.valueOf("articleText"))
            .title(ArticleTitle.valueOf("articleTitle"))
            .countries(CountryCodes.of(new CountryCode[]{CountryCode.valueOf("us")}))
            .editors(IntegerIds.of(new long[]{1,2}))
            .isPublished(GenericSwitch.positive())
            .sourcePage(SourcePage.builder()
                    .date(Date.today())
                    .url(SourceUrl.valueOf("https://www.google.co.jp/"))
                    .title(ArticleTitle.valueOf("title"))
                    .build())
            .topicTags(IntegerIds.of(new IntegerId[]{IntegerId.valueOf(12)}))
            .userComments(24)
            .sourceComments(24)
            .latestEditTimestamp(Timestamp.valueOf(1234567890))
            .registrationTimestamp(Timestamp.valueOf(1234567890))
            .build();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


}