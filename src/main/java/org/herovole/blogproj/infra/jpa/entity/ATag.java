package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.tag.TagEnglish;
import org.herovole.blogproj.domain.tag.TagJapanese;
import org.herovole.blogproj.domain.tag.TagUnit;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/*
CREATE TABLE a_tag (
        id INT PRIMARY KEY,
        name_en VARCHAR(31) NOT NULL,
        name_ja VARCHAR(15) NOT NULL,
        update_timestamp timestamp default current_timestamp on update current_timestamp,
        insert_timestamp timestamp default current_timestamp,
        delete_flag TINYINT(1) NOT NULL DEFAULT 0
        ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
 */

@Entity
@Table(name = "a_tag")
@Data
public class ATag implements Serializable {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_ja")
    private String nameJa;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;

    @Column(name = "delete_flag")
    private boolean deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATag aTag = (ATag) o;
        return nameEn.equals(aTag.nameEn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameEn);
    }

    public TagUnit toDomainObj() {
        return TagUnit.builder()
                .id(IntegerId.valueOf(id))
                .tagJapanese(TagJapanese.valueOf(nameJa))
                .tagEnglish(TagEnglish.valueOf(nameEn))
                .build();
    }
}
