package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.tag.topic.RealTagUnit;
import org.herovole.blogproj.domain.tag.topic.RealTagUnitWithStat;
import org.herovole.blogproj.domain.tag.topic.TagEnglish;
import org.herovole.blogproj.domain.tag.topic.TagJapanese;
import org.herovole.blogproj.domain.tag.topic.TagUnit;
import org.herovole.blogproj.domain.time.Timestamp;

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

    public static ATopicTag fromInsertDomainObj(IntegerId id, TagUnit tagUnit) {
        RealTagUnit tagUnit1 = (RealTagUnit) tagUnit;
        ATopicTag aTopicTag = new ATopicTag();
        aTopicTag.setId(id.intMemorySignature());
        aTopicTag.setNameEn(tagUnit1.getTagEnglish().memorySignature());
        aTopicTag.setNameJa(tagUnit1.getTagJapanese().memorySignature());
        aTopicTag.setUpdateTimestamp(LocalDateTime.now());
        aTopicTag.setInsertTimestamp(LocalDateTime.now());
        aTopicTag.setDeleteFlag(false);
        return aTopicTag;
    }

    public static ATopicTag fromUpdateDomainObj(TagUnit tagUnit) {
        RealTagUnit tagUnit1 = (RealTagUnit) tagUnit;
        ATopicTag aTopicTag = new ATopicTag();
        aTopicTag.setId(tagUnit1.getId().intMemorySignature());
        aTopicTag.setNameEn(tagUnit1.getTagEnglish().memorySignature());
        aTopicTag.setNameJa(tagUnit1.getTagJapanese().memorySignature());
        aTopicTag.setUpdateTimestamp(LocalDateTime.now());
        //aTopicTag.setInsertTimestamp(LocalDateTime.now());
        aTopicTag.setDeleteFlag(false);
        return aTopicTag;
    }


    public TagUnit toDomainObj() {
        return RealTagUnit.builder()
                .id(IntegerId.valueOf(id))
                .tagJapanese(TagJapanese.valueOf(nameJa))
                .tagEnglish(TagEnglish.valueOf(nameEn))
                .build();
    }

    public interface ATopicTagWithStat {
        int getId();

        String getName_en();

        String getName_ja();

        LocalDateTime getUpdate_timestamp();

        LocalDateTime getInsert_timestamp();

        boolean getDeleteFlag();

        int getArticle_count();

        default TagUnit toDomainObj() {
            return RealTagUnitWithStat.builder()
                    .id(IntegerId.valueOf(getId()))
                    .tagJapanese(TagJapanese.valueOf(getName_ja()))
                    .tagEnglish(TagEnglish.valueOf(getName_en()))
                    .lastUpdate(Timestamp.valueOf(getUpdate_timestamp()))
                    .articles(getArticle_count())
                    .build();
        }
    }

}
