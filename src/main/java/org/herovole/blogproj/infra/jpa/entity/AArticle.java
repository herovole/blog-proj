package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.article.Article;
import org.herovole.blogproj.domain.article.ArticleText;
import org.herovole.blogproj.domain.article.ArticleTitle;
import org.herovole.blogproj.domain.article.RealArticle;
import org.herovole.blogproj.domain.article.RealArticleSimplified;
import org.herovole.blogproj.domain.image.ImageName;
import org.herovole.blogproj.domain.source.SourcePage;
import org.herovole.blogproj.domain.source.SourceUrl;
import org.herovole.blogproj.domain.time.Date;
import org.herovole.blogproj.domain.time.Timestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "a_article")
@Data
public class AArticle implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "source_title")
    private String sourceTitle;

    @Column(name = "source_page")
    private String sourcePage;

    @Column(name = "source_date")
    private LocalDate sourceDate;

    @Column(name = "is_published")
    private boolean isPublished;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;

    @Column(name = "delete_flag")
    private boolean deleteFlag;

    public static AArticle fromInsertDomainObj(IntegerId id, Article article) {
        if (article.isEmpty() || id.isEmpty()) throw new EmptyRecordException();
        RealArticle article1 = (RealArticle) article;

        AArticle entity = fromDomainObj(article1);
        entity.setId(id.longMemorySignature());
        entity.setUpdateTimestamp(LocalDateTime.now());
        entity.setInsertTimestamp(LocalDateTime.now());
        return entity;
    }

    public static AArticle fromUpdateDomainObj(Article article) {
        if (article.isEmpty() || article.getArticleId().isEmpty()) throw new EmptyRecordException();
        RealArticle article1 = (RealArticle) article;

        AArticle entity = fromDomainObj(article1);
        entity.setId(article1.getArticleId().longMemorySignature());
        entity.setUpdateTimestamp(LocalDateTime.now());
        return entity;
    }

    private static AArticle fromDomainObj(Article article) {
        if (article.isEmpty()) throw new EmptyRecordException();
        RealArticle article1 = (RealArticle) article;

        AArticle entity = new AArticle();
        entity.setTitle(article1.getTitle().memorySignature());
        entity.setText(article1.getText().memorySignature());
        entity.setImageName(article1.getImage().memorySignature());

        SourcePage sourcePage = article1.getSourcePage();
        entity.setSourceTitle(sourcePage.getTitle().memorySignature());
        entity.setSourcePage(sourcePage.getUrl().memorySignature());
        entity.setSourceDate(sourcePage.getDate().toLocalDate());
        entity.setPublished(article1.getIsPublished().memorySignature());

        entity.setDeleteFlag(false);
        return entity;
    }

    public Article toDomainObj() {
        return RealArticle.builder()
                .articleId(IntegerId.valueOf(id))
                .title(ArticleTitle.valueOf(title))
                .text(ArticleText.valueOf(text))
                .image(ImageName.valueOf(imageName))
                .sourcePage(SourcePage.builder()
                        .url(SourceUrl.valueOf(sourcePage))
                        .title(ArticleTitle.valueOf(sourceTitle))
                        .date(Date.valueOf(sourceDate))
                        .build())
                .isPublished(GenericSwitch.valueOf(isPublished))
                .registrationTimestamp(Timestamp.valueOf(insertTimestamp))
                .latestEditTimestamp(Timestamp.valueOf(updateTimestamp))

                .build();
    }

    public Article toDomainObjSimplified() {
        return RealArticleSimplified.builder()
                .articleId(IntegerId.valueOf(id))
                .title(ArticleTitle.valueOf(title))
                .text(ArticleText.valueOf(text))
                .image(ImageName.valueOf(imageName))
                .sourcePage(SourcePage.builder()
                        .url(SourceUrl.valueOf(sourcePage))
                        .title(ArticleTitle.valueOf(sourceTitle))
                        .date(Date.valueOf(sourceDate))
                        .build())
                .isPublished(GenericSwitch.valueOf(isPublished))
                .registrationTimestamp(Timestamp.valueOf(insertTimestamp))
                .latestEditTimestamp(Timestamp.valueOf(updateTimestamp))

                .build();
    }
}
