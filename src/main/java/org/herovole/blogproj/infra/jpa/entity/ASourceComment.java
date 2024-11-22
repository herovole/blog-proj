package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.CommentText;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.RealCommentUnit;
import org.herovole.blogproj.domain.tag.CountryCode;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDateTime;

/*
    CREATE TABLE a_source_comment (
      id INT PRIMARY KEY,
      comment_id INT not null,
      comment_text TEXT,
      iso_2 CHAR(2),
      is_hidden TINYINT(1) NOT NULL DEFAULT 0,
      referring_comment_ids VARCHAR(127),

      update_timestamp timestamp default current_timestamp on update current_timestamp,
      insert_timestamp timestamp default current_timestamp,
      delete_flag TINYINT(1) NOT NULL DEFAULT 0,

      FOREIGN KEY (iso_2) REFERENCES m_country(iso_2) ON DELETE CASCADE
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

 */

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "a_source_comment")
@Data
public class ASourceComment implements Serializable {

    @Id
    @Column(name = "id")
    private long id;

    @EqualsAndHashCode.Include
    @Column(name = "comment_id")
    private long commentId;

    @EqualsAndHashCode.Include
    @Column(name = "article_id")
    private long articleId;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "iso_2")
    private String iso2;

    @Column(name = "is_hidden")
    private boolean isHidden;

    @Column(name = "referring_comment_ids")
    private String referringCommentIds;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @Column(name = "insert_timestamp")
    private LocalDateTime insertTimestamp;

    @Column(name = "delete_flag")
    private boolean deleteFlag;


    public static ASourceComment fromInsertDomainObj(IntegerId articleId, CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        ASourceComment aSourceComment = fromDomainObj(commentUnit);
        aSourceComment.setArticleId(articleId.longMemorySignature());
        aSourceComment.setUpdateTimestamp(LocalDateTime.now());
        aSourceComment.setInsertTimestamp(LocalDateTime.now());
        aSourceComment.setDeleteFlag(false);
        return aSourceComment;
    }

    public static ASourceComment fromUpdateDomainObj(IntegerId articleId, CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        RealCommentUnit commentUnit1 = (RealCommentUnit) commentUnit;
        ASourceComment sourceComment = fromDomainObj(commentUnit);
        sourceComment.setId(commentUnit1.getCommentSerialNumber().longMemorySignature());
        sourceComment.setArticleId(articleId.longMemorySignature());
        sourceComment.setUpdateTimestamp(LocalDateTime.now());
        sourceComment.setDeleteFlag(false);
        return sourceComment;
    }

    private static ASourceComment fromDomainObj(CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        RealCommentUnit commentUnit1 = (RealCommentUnit) commentUnit;
        ASourceComment sourceComment = new ASourceComment();
        sourceComment.setCommentId(commentUnit1.getCommentId().longMemorySignature());
        sourceComment.setCommentText(commentUnit1.getCommentText().memorySignature());
        sourceComment.setIso2(commentUnit1.getCountry().memorySignature());
        sourceComment.setHidden(commentUnit1.getIsHidden().memorySignature());
        sourceComment.setReferringCommentIds(commentUnit1.getReferringCommentIds().commaSeparatedMemorySignature());
        return sourceComment;
    }

    public static String fromDeleteDomainObj(IntegerId articleId, CommentUnit commentUnit) {
        return MessageFormat.format("Delete From a_source_comment Where article_id = {0} And comment_id = {1}",
                articleId.letterSignature(), commentUnit.getCommentId().letterSignature());
    }

    public CommentUnit toDomainObj() {
        return RealCommentUnit.builder()
                .commentSerialNumber(IntegerId.valueOf(id))
                .commentId(IntegerId.valueOf(commentId))
                .commentText(CommentText.valueOf(commentText))
                .country(CountryCode.valueOf(iso2))
                .isHidden(GenericSwitch.valueOf(isHidden))
                .referringCommentIds(IntegerIds.of(referringCommentIds))
                .build();
    }
}
