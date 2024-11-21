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
      insert_timestamp timestamp default current_timestamp,

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


    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;


    public static AArticleHasEditor fromInsertDomainObj(IntegerId articleId, IntegerId editor) {
        if (editor.isEmpty()) throw new EmptyRecordException();
        AArticleHasEditor entity = new AArticleHasEditor();
        entity.setArticleId(articleId.longMemorySignature());
        entity.setEditorId(editor.longMemorySignature());
        entity.setInsertTimestamp(LocalDateTime.now());
        return entity;
    }

    public IntegerId toEditorId() {
        return IntegerId.valueOf(editorId);
    }
}
