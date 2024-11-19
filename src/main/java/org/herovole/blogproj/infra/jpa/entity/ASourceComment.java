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

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "article_id")
    private int articleId;

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


    public CommentUnit toDomainObj() {
        return RealCommentUnit.builder()
                .commentId(IntegerId.valueOf(commentId))
                .commentText(CommentText.valueOf(commentText))
                .country(CountryCode.valueOf(iso2))
                .isHidden(GenericSwitch.valueOf(isHidden))
                .referringCommentIds(IntegerIds.of(referringCommentIds))
                .build();
    }
}
