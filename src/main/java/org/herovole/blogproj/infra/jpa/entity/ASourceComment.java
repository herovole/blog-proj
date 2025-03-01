package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.RealSourceCommentUnit;
import org.herovole.blogproj.domain.tag.country.CountryCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDateTime;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "a_source_comment")
@Data
public class ASourceComment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "iso_2", columnDefinition = "CHAR")
    private String iso2;

    @Column(name = "is_hidden")
    private boolean isHidden;

    @Column(name = "referring_comment_ids")
    private String referringCommentIds;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @CreationTimestamp
    @Column(name = "insert_timestamp", updatable = false)
    private LocalDateTime insertTimestamp;

    @Column(name = "delete_flag")
    private boolean deleteFlag;


    public static ASourceComment fromInsertDomainObj(IntegerId articleId, CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        ASourceComment aSourceComment = fromDomainObj(commentUnit);
        aSourceComment.setArticleId(articleId.longMemorySignature());
        aSourceComment.setDeleteFlag(false);
        return aSourceComment;
    }

    public static ASourceComment fromUpdateDomainObj(CommentUnit before, CommentUnit after) {
        if (after.isEmpty()) throw new EmptyRecordException();
        RealSourceCommentUnit before1 = (RealSourceCommentUnit) before;
        ASourceComment sourceComment = fromDomainObj(after);
        sourceComment.setId(before1.getCommentSerialNumber().longMemorySignature());
        sourceComment.setArticleId(before1.getArticleId().longMemorySignature());
        sourceComment.setDeleteFlag(false);
        return sourceComment;
    }

    private static ASourceComment fromDomainObj(CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        RealSourceCommentUnit commentUnit1 = (RealSourceCommentUnit) commentUnit;
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
        return RealSourceCommentUnit.builder()
                .commentSerialNumber(IntegerId.valueOf(id))
                .commentId(IntegerId.valueOf(commentId))
                .commentText(CommentText.valueOf(commentText))
                .country(CountryCode.valueOf(iso2))
                .isHidden(GenericSwitch.valueOf(isHidden))
                .referringCommentIds(IntegerIds.of(referringCommentIds))
                .build();
    }
}
