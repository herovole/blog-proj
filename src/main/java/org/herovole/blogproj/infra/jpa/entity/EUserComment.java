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
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.article.ArticleTitle;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.CommentUnitWithDepth;
import org.herovole.blogproj.domain.comment.HandleName;
import org.herovole.blogproj.domain.comment.RealUserCommentUnit;
import org.herovole.blogproj.domain.comment.admincommentunit.AdminCommentUnit;
import org.herovole.blogproj.domain.publicuser.DailyUserId;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;

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
    private int commentId;

    @EqualsAndHashCode.Include
    @Column(name = "article_id")
    private long articleId;

    @Column(name = "handle_name")
    private String handleName;
    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "is_hidden")
    private boolean isHidden;

    @Column(name = "referring_comment_ids")
    private String referringCommentIds;

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
        eUserComment.setCommentId(inArticleCommentId.intMemorySignature());
        eUserComment.setArticleId(commentUnit.getArticleId().longMemorySignature());
        eUserComment.setDeleteFlag(false);
        return eUserComment;
    }

    public static EUserComment fromUpdateDomainObj(CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        RealUserCommentUnit commentUnit1 = (RealUserCommentUnit) commentUnit;
        EUserComment userComment = fromDomainObj(commentUnit);
        userComment.setId(commentUnit1.getCommentSerialNumber().longMemorySignature());
        userComment.setCommentId(commentUnit1.getCommentId().intMemorySignature());
        userComment.setArticleId(commentUnit.getArticleId().longMemorySignature());
        userComment.setDeleteFlag(false);
        return userComment;
    }

    private static EUserComment fromDomainObj(CommentUnit commentUnit) {
        if (commentUnit.isEmpty()) throw new EmptyRecordException();
        RealUserCommentUnit commentUnit1 = (RealUserCommentUnit) commentUnit;
        EUserComment userComment = new EUserComment();
        userComment.setHandleName(commentUnit1.getHandleName().memorySignature());
        userComment.setCommentText(commentUnit1.getCommentText().memorySignature());
        userComment.setHidden(commentUnit1.getIsHidden().memorySignature());
        userComment.setReferringCommentIds(commentUnit1.getReferringCommentIds().commaSeparatedMemorySignature());
        userComment.setDailyUserId(commentUnit1.getDailyUserId().memorySignature());
        userComment.setUserId(commentUnit1.getPublicUserId().longMemorySignature());
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
                .articleId(IntegerId.valueOf(articleId))
                .handleName(HandleName.valueOf(handleName))
                .commentText(CommentText.valueOf(commentText))
                .isHidden(GenericSwitch.valueOf(isHidden))
                .referringCommentIds(IntegerIds.of(referringCommentIds))
                .dailyUserId(DailyUserId.valueOf(dailyUserId))
                .publicUserId(IntegerPublicUserId.valueOf(userId))
                .ip(IPv4Address.valueOf(aton))
                .postTimestamp(Timestamp.valueOf(insertTimestamp))
                .build();
    }

    public interface EUserCommentWithRating {
        @Value("#{target.id}")
        long getId();

        @Value("#{target.comment_id}")
        int getCommentId();

        @Value("#{target.article_id}")
        long getArticleId();

        @Value("#{target.handle_name}")
        String getHandleName();

        @Value("#{target.comment_text}")
        String getCommentText();

        @Value("#{target.is_hidden}")
        boolean getIsHidden();

        @Value("#{target.referring_comment_ids}")
        String getReferringCommentIds();

        @Value("#{target.daily_user_id}")
        String getDailyUserId();

        @Value("#{target.user_id}")
        long getUserId();

        @Value("#{target.aton}")
        long getAton();

        @Value("#{target.update_timestamp}")
        LocalDateTime getUpdateTimestamp();

        @Value("#{target.insert_timestamp}")
        LocalDateTime getInsertTimestamp();


        @Value("#{target.delete_flag}")
        boolean getDeleteFlag();

        @Value("#{target.likes}")
        int getLikes();

        @Value("#{target.dislikes}")
        int getDisLikes();

        default CommentUnit toDomainObj() {
            return RealUserCommentUnit.builder()
                    .commentSerialNumber(IntegerId.valueOf(this.getId()))
                    .commentId(IntegerId.valueOf(this.getCommentId()))
                    .articleId(IntegerId.valueOf(this.getArticleId()))
                    .handleName(HandleName.valueOf(this.getHandleName()))
                    .commentText(CommentText.valueOf(this.getCommentText()))
                    .isHidden(GenericSwitch.valueOf(this.getIsHidden()))
                    .referringCommentIds(IntegerIds.of(this.getReferringCommentIds()))
                    .dailyUserId(DailyUserId.valueOf(this.getDailyUserId()))
                    .publicUserId(IntegerPublicUserId.valueOf(this.getUserId()))
                    .ip(IPv4Address.valueOf(this.getAton()))
                    .postTimestamp(Timestamp.valueOf(this.getInsertTimestamp()))
                    .likes(this.getLikes())
                    .dislikes(this.getDisLikes())
                    .build();
        }
    }

    public interface EUserCommentForAdmin {
        @Value("#{target.id}")
        long getId();

        @Value("#{target.comment_id}")
        int getCommentId();

        @Value("#{target.article_id}")
        long getArticleId();

        @Value("#{target.handle_name}")
        String getHandleName();

        @Value("#{target.comment_text}")
        String getCommentText();

        @Value("#{target.is_hidden}")
        boolean getIsHidden();

        @Value("#{target.referring_comment_ids}")
        String getReferringCommentIds();

        @Value("#{target.daily_user_id}")
        String getDailyUserId();

        @Value("#{target.user_id}")
        long getUserId();

        @Value("#{target.user_banned_until}")
        LocalDateTime getUserBannedUntil();

        @Value("#{target.aton}")
        long getAton();

        @Value("#{target.ip_banned_until}")
        LocalDateTime getIpBannedUntil();

        @Value("#{target.update_timestamp}")
        LocalDateTime getUpdateTimestamp();

        @Value("#{target.insert_timestamp}")
        LocalDateTime getInsertTimestamp();

        @Value("#{target.delete_flag}")
        boolean getDeleteFlag();

        @Value("#{target.article_title}")
        String getArticleTitle();

        default AdminCommentUnit toDomainObj() {
            return AdminCommentUnit.builder()
                    .userCommentUnit(
                            new CommentUnitWithDepth(
                                    RealUserCommentUnit.builder()
                                            .commentSerialNumber(IntegerId.valueOf(this.getId()))
                                            .commentId(IntegerId.valueOf(this.getCommentId()))
                                            .articleId(IntegerId.valueOf(this.getArticleId()))
                                            .handleName(HandleName.valueOf(this.getHandleName()))
                                            .commentText(CommentText.valueOf(this.getCommentText()))
                                            .isHidden(GenericSwitch.valueOf(this.getIsHidden()))
                                            .referringCommentIds(IntegerIds.of(this.getReferringCommentIds()))
                                            .dailyUserId(DailyUserId.valueOf(this.getDailyUserId()))
                                            .publicUserId(IntegerPublicUserId.valueOf(this.getUserId()))
                                            .ip(IPv4Address.valueOf(this.getAton()))
                                            .postTimestamp(Timestamp.valueOf(this.getInsertTimestamp()))
                                            .likes(0)
                                            .dislikes(0)
                                            .build()
                                    , 0)
                    )
                    .commentUserBannedUntil(Timestamp.valueOf(this.getUserBannedUntil()))
                    .commentIpBannedUntil(Timestamp.valueOf(this.getIpBannedUntil()))
                    .title(ArticleTitle.valueOf(this.getArticleTitle()))
                    .build();
        }
    }
}
