package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.IntegerId;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
    CREATE TABLE a_article_has_editor (
      id INT PRIMARY KEY,
      article_id INT not null,
      editor_id INT not null,
      update_timestamp timestamp default current_timestamp on update current_timestamp,
      insert_timestamp timestamp default current_timestamp,
      delete_flag TINYINT(1) NOT NULL DEFAULT 0,

      FOREIGN KEY (article_id) REFERENCES a_article(id) ON DELETE CASCADE,
      FOREIGN KEY (editor_id) REFERENCES m_editor(id) ON DELETE CASCADE

    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
 */

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "a_article_has_editor")
@Data
public class AArticleHasEditor implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "article_id")
    private long articleId;

    @Column(name = "editor_id")
    private long editorId;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;

    @Column(name = "delete_flag")
    private boolean deleteFlag;


    public IntegerId toEditorId() {
        return IntegerId.valueOf(editorId);
    }
}
