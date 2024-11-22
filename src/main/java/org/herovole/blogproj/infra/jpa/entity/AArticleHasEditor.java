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
@Table(name = "a_article_has_editor")
@Data
public class AArticleHasEditor implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "article_id")
    private long articleId;

    @Column(name = "editor_id")
    private int editorId;


    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;


    public static AArticleHasEditor fromInsertDomainObj(IntegerId articleId, IntegerId editor) {
        if (editor.isEmpty()) throw new EmptyRecordException();
        AArticleHasEditor entity = new AArticleHasEditor();
        entity.setArticleId(articleId.longMemorySignature());
        entity.setEditorId(editor.intMemorySignature());
        entity.setInsertTimestamp(LocalDateTime.now());
        return entity;
    }

    public static String fromDeleteDomainObj(IntegerId articleId, IntegerId editor) {
        return MessageFormat.format("Delete From a_article_has_editor Where article_id = {0} And editor_id = {1}",
                articleId.letterSignature(), editor.letterSignature());
    }

    public IntegerId toEditorId() {
        return IntegerId.valueOf(editorId);
    }
}
