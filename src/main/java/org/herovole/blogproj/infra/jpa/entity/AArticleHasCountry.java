package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.tag.country.CountryCode;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDateTime;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "a_article_has_country")
@Data
public class AArticleHasCountry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @EqualsAndHashCode.Include
    @Column(name = "article_id")
    private long articleId;

    @EqualsAndHashCode.Include
    @Column(name = "iso_2", columnDefinition = "CHAR")
    private String iso2;


    @CreationTimestamp
    @Column(name = "insert_timestamp", updatable = false)
    private LocalDateTime insertTimestamp;


    public static AArticleHasCountry fromInsertDomainObj(IntegerId articleId, CountryCode countryCode) {
        if (countryCode.isEmpty()) throw new EmptyRecordException();
        AArticleHasCountry entity = new AArticleHasCountry();
        entity.setArticleId(articleId.longMemorySignature());
        entity.setIso2(countryCode.memorySignature());
        return entity;
    }

    public static String fromDeleteDomainObj(IntegerId articleId, CountryCode countryCode) {
        return MessageFormat.format("Delete From a_article_has_country where article_id = {0} And iso_2 = ''{1}''",
                articleId.letterSignature(), countryCode.letterSignature());
    }

    public CountryCode toIso2() {
        return CountryCode.valueOf(iso2);
    }
}
