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
@Table(name = "e_user_comment")
@Data
public class EUserComment implements Serializable {

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

    @Column(name = "user_id")
    private long userId;

    @Column(name = "aton")
    private long aton;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @CreationTimestamp
    @Column(name = "insert_timestamp", updatable = false)
    private LocalDateTime insertTimestamp;

    @Column(name = "delete_flag")
    private boolean deleteFlag;


    public static EUserComment fromInsertDomainObj(IntegerId inArticleCommentId, CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        EUserComment eUserComment = fromDomainObj(commentUnit);
        eUserComment.setCommentId(inArticleCommentId.longMemorySignature());
        eUserComment.setArticleId(commentUnit.getArticleId().longMemorySignature());
        eUserComment.setDeleteFlag(false);
        return eUserComment;
    }

    public static EUserComment fromUpdateDomainObj(CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        RealUserCommentUnit commentUnit1 = (RealUserCommentUnit) commentUnit;
        EUserComment userComment = fromDomainObj(commentUnit);
        userComment.setId(commentUnit1.getCommentSerialNumber().longMemorySignature());
        userComment.setCommentId(commentUnit1.getCommentId().longMemorySignature());
        userComment.setArticleId(commentUnit.getArticleId().longMemorySignature());
        userComment.setDeleteFlag(false);
        return userComment;
    }

    private static EUserComment fromDomainObj(CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        RealUserCommentUnit commentUnit1 = (RealUserCommentUnit) commentUnit;
        EUserComment userComment = new EUserComment();
        userComment.setCommentText(commentUnit1.getCommentText().memorySignature());
        userComment.setHidden(commentUnit1.getIsHidden().memorySignature());
        userComment.setReferringCommentIds(commentUnit1.getReferringCommentIds().commaSeparatedMemorySignature());
        userComment.setLikes(commentUnit1.getLikes());
        userComment.setDislikes(commentUnit1.getDislikes());
        userComment.setDailyUserId(commentUnit1.getDailyUserId().memorySignature());
        //userComment.setUserId(commentUnit1.getUuId().letterSignature())
        userComment.setAton(commentUnit1.getIp().aton());
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
