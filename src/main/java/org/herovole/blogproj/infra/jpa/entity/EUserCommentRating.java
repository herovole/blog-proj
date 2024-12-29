package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.rating.Rating;
import org.herovole.blogproj.domain.comment.rating.RatingLog;
import org.herovole.blogproj.domain.comment.rating.RealRatingLog;
import org.herovole.blogproj.domain.user.IntegerPublicUserId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "e_user_comment_rating")
@Data
public class EUserCommentRating implements Serializable {

    public static EUserCommentRating fromDomainObjectInsert(RatingLog ratingLog) {
        RealRatingLog ratingLog1 = (RealRatingLog) ratingLog;

        EUserCommentRating entity = new EUserCommentRating();
        entity.setCommentSerialNumber(ratingLog1.getCommentSerialNumber().longMemorySignature());
        entity.setPublicUserId(ratingLog1.getPublicUserId().longMemorySignature());
        entity.setAton(ratingLog1.getIp().aton());
        entity.setRating(ratingLog1.getRating().memorySignature());
        entity.setDeleteFlag(false);
        return entity;
    }

    public static EUserCommentRating fromDomainObjectUpdate(IntegerId logId, RatingLog ratingLog) {
        RealRatingLog ratingLog1 = (RealRatingLog) ratingLog;

        EUserCommentRating entity = new EUserCommentRating();
        entity.setId(logId.longMemorySignature());
        entity.setCommentSerialNumber(ratingLog1.getCommentSerialNumber().longMemorySignature());
        entity.setPublicUserId(ratingLog1.getPublicUserId().longMemorySignature());
        entity.setAton(ratingLog1.getIp().aton());
        entity.setRating(ratingLog1.getRating().memorySignature());
        entity.setDeleteFlag(false);
        return entity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @EqualsAndHashCode.Include
    @Column(name = "comment_serial_number")
    private long commentSerialNumber;

    @Column(name = "public_user_id")
    private long publicUserId;

    @Column(name = "aton")
    private long aton;

    @Column(name = "rating")
    private int rating;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @CreationTimestamp
    @Column(name = "insert_timestamp", updatable = false)
    private LocalDateTime insertTimestamp;

    @Column(name = "delete_flag")
    private boolean deleteFlag;

    public RatingLog toDomainObj() {
        return RealRatingLog.builder()
                .logId(IntegerId.valueOf(this.id))
                .commentSerialNumber(IntegerId.valueOf(this.commentSerialNumber))
                .publicUserId(IntegerPublicUserId.valueOf(this.publicUserId))
                .ip(IPv4Address.valueOf(this.aton))
                .rating(Rating.valueOf(this.rating))
                .build();
    }
}
