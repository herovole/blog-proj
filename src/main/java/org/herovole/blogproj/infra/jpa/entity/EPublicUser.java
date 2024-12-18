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
@Table(name = "e_public_user")
@Data
public class EPublicUser implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "uu_id", columnDefinition = "CHAR")
    private String uuId;

    @Column(name = "banned_until")
    private LocalDateTime bannedUntil;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @CreationTimestamp
    @Column(name = "insert_timestamp", updatable = false)
    private LocalDateTime insertTimestamp;


    public static EPublicUser fromInsertDomainObj(IntegerId inArticleCommentId, CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        EPublicUser eUserComment = fromDomainObj(commentUnit);
        eUserComment.setCommentId(inArticleCommentId.longMemorySignature());
        eUserComment.setArticleId(commentUnit.getArticleId().longMemorySignature());
        eUserComment.setDeleteFlag(false);
        return eUserComment;
    }

    public static EPublicUser fromUpdateDomainObj(CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        RealUserCommentUnit commentUnit1 = (RealUserCommentUnit) commentUnit;
        EPublicUser userComment = fromDomainObj(commentUnit);
        userComment.setId(commentUnit1.getCommentSerialNumber().longMemorySignature());
        userComment.setCommentId(commentUnit1.getCommentId().longMemorySignature());
        userComment.setArticleId(commentUnit.getArticleId().longMemorySignature());
        userComment.setDeleteFlag(false);
        return userComment;
    }

    private static EPublicUser fromDomainObj(CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        RealUserCommentUnit commentUnit1 = (RealUserCommentUnit) commentUnit;
        EPublicUser userComment = new EPublicUser();
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
