package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.IntegerId;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDateTime;

/*
    CREATE TABLE a_article_has_topic_tag (
      id INT PRIMARY KEY,
      article_id INT not null,
      topic_tag_id INT not null,
      insert_timestamp timestamp default current_timestamp,

      FOREIGN KEY (article_id) REFERENCES a_article(id) ON DELETE CASCADE,
      FOREIGN KEY (topic_tag_id) REFERENCES a_topic_tag(id) ON DELETE CASCADE

    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

 */

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "a_article_has_topic_tag")
@Data
public class AArticleHasTopicTag implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "article_id")
    private long articleId;

    @Column(name = "topic_tag_id")
    private long topicTagId;

    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;


    public static AArticleHasTopicTag fromInsertDomainObj(IntegerId articleId, IntegerId topicTag) {
        if (topicTag.isEmpty()) throw new EmptyRecordException();
        AArticleHasTopicTag entity = new AArticleHasTopicTag();
        entity.setArticleId(articleId.longMemorySignature());
        entity.setTopicTagId(topicTag.longMemorySignature());
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
