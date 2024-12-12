package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.CommentText;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.RealUserCommentUnit;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.domain.user.DailyUserId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDateTime;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "a_user_comment")
@Data
public class AUserComment implements Serializable {

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

    @Column(name = "is_hidden")
    private boolean isHidden;

    @Column(name = "referring_comment_ids")
    private String referringCommentIds;

    @Column(name = "likes")
    private int likes;

    @Column(name = "dislikes")
    private int dislikes;

    @Column(name = "daily_user_id", columnDefinition = "CHAR")
    private String dailyUserId;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @CreationTimestamp
    @Column(name = "insert_timestamp", updatable = false)
    private LocalDateTime insertTimestamp;

    @Column(name = "delete_flag")
    private boolean deleteFlag;


    public static AUserComment fromInsertDomainObj(IntegerId articleId, CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        AUserComment aUserComment = fromDomainObj(commentUnit);
        aUserComment.setArticleId(articleId.longMemorySignature());
        aUserComment.setDeleteFlag(false);
        return aUserComment;
    }

    public static AUserComment fromUpdateDomainObj(IntegerId articleId, CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        RealUserCommentUnit commentUnit1 = (RealUserCommentUnit) commentUnit;
        AUserComment userComment = fromDomainObj(commentUnit);
        userComment.setId(commentUnit1.getCommentSerialNumber().longMemorySignature());
        userComment.setArticleId(articleId.longMemorySignature());
        userComment.setDeleteFlag(false);
        return userComment;
    }

    private static AUserComment fromDomainObj(CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        RealUserCommentUnit commentUnit1 = (RealUserCommentUnit) commentUnit;
        AUserComment userComment = new AUserComment();
        userComment.setCommentId(commentUnit1.getCommentId().longMemorySignature());
        userComment.setCommentText(commentUnit1.getCommentText().memorySignature());
        userComment.setHidden(commentUnit1.getIsHidden().memorySignature());
        userComment.setReferringCommentIds(commentUnit1.getReferringCommentIds().commaSeparatedMemorySignature());
        userComment.setLikes(commentUnit1.getLikes());
        userComment.setDislikes(commentUnit1.getDislikes());
        userComment.setDailyUserId(commentUnit1.getDailyUserId().memorySignature());
        return userComment;
    }

    public static String fromDeleteDomainObj(IntegerId articleId, CommentUnit commentUnit) {
        return MessageFormat.format("Delete From a_source_comment Where article_id = {0} And comment_id = {1}",
                articleId.letterSignature(), commentUnit.getCommentId().letterSignature());
    }

    public CommentUnit toDomainObj() {
        return RealUserCommentUnit.builder()
                .commentSerialNumber(IntegerId.valueOf(id))
                .commentId(IntegerId.valueOf(commentId))
                .commentText(CommentText.valueOf(commentText))
                .isHidden(GenericSwitch.valueOf(isHidden))
                .referringCommentIds(IntegerIds.of(referringCommentIds))
                .likes(likes)
                .dislikes(dislikes)
                .dailyUserId(DailyUserId.valueOf(dailyUserId))
                .postTimestamp(Timestamp.valueOf(insertTimestamp))
                .build();
    }
}
