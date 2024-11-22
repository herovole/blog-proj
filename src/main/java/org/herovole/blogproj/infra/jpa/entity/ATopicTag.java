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


@Entity
@Table(name = "a_topic_tag")
@Data
public class ATopicTag implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

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
        ATopicTag aTopicTag = (ATopicTag) o;
        return nameEn.equals(aTopicTag.nameEn);
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
