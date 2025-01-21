package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.EUserCommentRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EUserCommentRatingRepository extends JpaRepository<EUserCommentRating, Long> {

    @Query(value = "Select * from e_user_comment_rating limit 1", nativeQuery = true)
    EUserCommentRating findOne();

    @Query(value = "Select * from e_user_comment_rating " +
            " where " +
            "   public_user_id = :publicUserId " +
            " AND comment_serial_number = :commentSerialNumber " +
            " AND delete_flag = 0", nativeQuery = true)
    List<EUserCommentRating> findActiveHistoryByUserId(
            @Param("publicUserId") long publicUserId,
            @Param("commentSerialNumber") long commentSerialNumber);

    @Query(value = "Select r.* from " +
            "   e_user_comment_rating r, " +
            "   e_user_comment c " +
            " where " +
            "   r.comment_serial_number = c.id And " +
            "   r.public_user_id = :publicUserId And " +
            "   c.article_id = :articleId And " +
            "   r.delete_flag = 0 " +
            " Order By r.comment_serial_number ", nativeQuery = true)
    List<EUserCommentRating> findActiveHistoryByUserIdAndArticleId(
            @Param("publicUserId") long publicUserId,
            @Param("articleId") long articleId);

    @Query(value = "Select * from e_user_comment_rating " +
            " where " +
            "   aton = :aton AND update_timestamp between :timestampBeginning AND :timestampEnding " +
            " AND comment_serial_number = :commentSerialNumber " +
            " AND delete_flag = 0", nativeQuery = true)
    List<EUserCommentRating> findActiveHistoryByIpAndTimestampRange(
            @Param("aton") long aton,
            @Param("timestampBeginning") LocalDateTime timestampBeginning,
            @Param("timestampEnding") LocalDateTime timestampEnding,
            @Param("commentSerialNumber") long commentSerialNumber
    );

    @Query(value = "Select r.* from " +
            "   e_user_comment_rating r, " +
            "   e_user_comment c " +
            " where " +
            "   r.comment_serial_number = c.id And " +
            "   r.aton = :aton And " +
            "   r.update_timestamp between :timestampBeginning AND :timestampEnding And " +
            "   c.article_id = :articleId And " +
            "   r.delete_flag = 0 " +
            " Order By r.comment_serial_number ", nativeQuery = true)
    List<EUserCommentRating> findActiveHistoryByIpAndTimestampRangeAndArticleId(
            @Param("aton") long aton,
            @Param("timestampBeginning") LocalDateTime timestampBeginning,
            @Param("timestampEnding") LocalDateTime timestampEnding,
            @Param("articleId") long articleId);
}
