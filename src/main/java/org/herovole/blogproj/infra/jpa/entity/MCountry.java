package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.tag.country.CountryCode;
import org.herovole.blogproj.domain.tag.country.CountryTagUnit;
import org.herovole.blogproj.domain.tag.country.RealCountryTagUnit;
import org.herovole.blogproj.domain.tag.topic.TagEnglish;
import org.herovole.blogproj.domain.tag.topic.TagJapanese;

import java.io.Serializable;
import java.time.LocalDateTime;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "m_country")
@Data
public class MCountry implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_ja")
    private String nameJa;

    @EqualsAndHashCode.Include
    @Column(name = "iso_2", columnDefinition = "CHAR")
    private String iso2;

    @Column(name = "iso_3", columnDefinition = "CHAR")
    private String iso3;

    @Column(name = "icon_base")
    private String iconBase;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;

    public CountryTagUnit toDomainObj() {
        return RealCountryTagUnit.builder()
                .id(CountryCode.valueOf(iso2))
                .tagJapanese(TagJapanese.valueOf(nameJa))
                .tagEnglish(TagEnglish.valueOf(nameEn))
                .build();
    }


}
