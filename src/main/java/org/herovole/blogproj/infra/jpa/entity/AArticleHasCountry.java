package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.tag.CountryCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
    CREATE TABLE a_article_has_country (
      id INT PRIMARY KEY,
      article_id INT not null,
      iso_2 CHAR(2) not null,
      insert_timestamp timestamp default current_timestamp,

      FOREIGN KEY (article_id) REFERENCES a_article(id) ON DELETE CASCADE,
      FOREIGN KEY (iso_2) REFERENCES m_country(iso_2) ON DELETE CASCADE

    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

 */

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "a_article_has_country")
@Data
public class AArticleHasCountry implements Serializable {

    @Id
    @Column(name = "id")
    private long id;

    @EqualsAndHashCode.Include
    @Column(name = "article_id")
    private long articleId;

    @EqualsAndHashCode.Include
    @Column(name = "iso_2")
    private String iso2;


    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;


    public static AArticleHasCountry fromInsertDomainObj(IntegerId articleId, CountryCode countryCode) {
        if (countryCode.isEmpty()) throw new EmptyRecordException();
        AArticleHasCountry entity = new AArticleHasCountry();
        entity.setArticleId(articleId.longMemorySignature());
        entity.setIso2(countryCode.memorySignature());
        entity.setInsertTimestamp(LocalDateTime.now());
        return entity;
    }

    public CountryCode toIso2() {
        return CountryCode.valueOf(iso2);
    }
}
