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

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDateTime;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "a_article_has_topic_tag")
@Data
public class AArticleHasTopicTag implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "article_id")
    private long articleId;

    @Column(name = "topic_tag_id")
    private int topicTagId;

    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;


    public static AArticleHasTopicTag fromInsertDomainObj(IntegerId articleId, IntegerId topicTag) {
        if (topicTag.isEmpty()) throw new EmptyRecordException();
        AArticleHasTopicTag entity = new AArticleHasTopicTag();
        entity.setArticleId(articleId.longMemorySignature());
        entity.setTopicTagId(topicTag.intMemorySignature());
        entity.setInsertTimestamp(LocalDateTime.now());
        return entity;
    }

    public static String fromDeleteDomainObj(IntegerId articleId, IntegerId topicTag) {
        return MessageFormat.format("Delete From a_article_has_topic_tag Where article_id = {0} And topic_tag_id = {1}",
                articleId.letterSignature(), topicTag.letterSignature());
    }

    public IntegerId toTopicTagId() {
        return IntegerId.valueOf(topicTagId);
    }
}
