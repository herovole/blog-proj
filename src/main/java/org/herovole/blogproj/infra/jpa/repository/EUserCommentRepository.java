package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.EUserComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EUserCommentRepository extends JpaRepository<EUserComment, Long> {

    @Query(value = "Select * from e_user_comment limit 1", nativeQuery = true)
    EUserComment findOne();

    @Query(value = "Select max(id) from e_user_comment limit 1", nativeQuery = true)
    Long findMaxId();

    @Query(value = "Select max(comment_id) " +
            "From e_user_comment " +
            "Where article_id = :articleId " +
            "limit 1", nativeQuery = true)
    Integer findMaxCommentId(@Param("articleId") int articleId);

    @Query(value = "Select c.*, count(rn.id) as dislikes, count(rp.id) as likes " +
            " From " +
            "   e_user_comment c " +

            " Left Join " +
            "   e_user_comment_rating rn " +
            " On " +
            "   c.id = rn.comment_serial_number " +
            " And " +
            "   rn.rating < 0 And rn.delete_flag = 0 " +
            " Left Join " +
            "   e_user_comment_rating rp " +
            " On " +
            "   c.id = rp.comment_serial_number " +
            " And " +
            "   rp.rating > 0 And rp.delete_flag = 0 " +

            " Where " +
            "   c.article_id = :articleId " +
            " Group By c.id " +
            " Order By c.comment_id ", nativeQuery = true)
    List<EUserComment.EUserCommentWithRating> findByArticleId(@Param("articleId") long articleId);

    @Query(value = "Select COUNT(*) From e_user_comment Where article_id = :articleId", nativeQuery = true)
    int countByArticleId(@Param("articleId") long articleId);

    @Query(value = "  Select  " +
            "    c.*,  " +
            "    u.banned_until as user_banned_until,  " +
            "    i.banned_until as ip_banned_until," +
            "    count(ra.id) as report_count," +
            "    count(rb.id) as unhandled_report_count  " +
            "  From " +
            "    e_user_comment c  " +
            "  Left Join " +
            "    e_public_user u  " +
            "  On  " +
            "    c.public_user_id = u.id  " +
            "  Left Join " +
            "    e_public_ip i  " +
            "  On  " +
            "    c.aton = i.aton  " +
            "  Left Join " +
            "    e_user_comment_report ra  " +
            "  On  " +
            "    ra.comment_serial_number = c.id  " +
            "  Left Join " +
            "    e_user_comment_report rb  " +
            "  On  " +
            "    rb.comment_serial_number = c.id  And rb.is_handled = '0'" +
            "  Where  " +
            "   (" +
            "     c.comment_text like Concat('%', :keyword1, '%') " +
            "   OR " +
            "     c.handle_name like Concat('%', :keyword1, '%') " +
            "   OR :keyword1 is null " +
            "   )" +
            "   And " +
            "   (" +
            "     c.comment_text like Concat('%', :keyword2, '%') " +
            "   OR " +
            "     c.handle_name like Concat('%', :keyword2, '%') " +
            "   OR :keyword2 is null " +
            "   )" +
            "   And " +
            "   (" +
            "     c.comment_text like Concat('%', :keyword3, '%') " +
            "   OR " +
            "     c.handle_name like Concat('%', :keyword3, '%') " +
            "   OR :keyword3 is null " +
            "   )" +
            "   And  " +
            "   (" +
            "     c.update_timestamp between :timestampFrom And :timestampTo " +
            "   OR " +
            "     c.insert_timestamp between :timestampFrom And :timestampTo " +
            "   ) " +
            "  Group By c.id  " +
            "  Having  " +
            "    :minReportCount <= report_count  " +
            "  And  " +
            "    :minUnhandledReportCount <= unhandled_report_count  " +
            "  Order By c.update_timestamp DESC  " +
            "  Limit :limit Offset :offset " +
            "", nativeQuery = true)
    List<EUserComment.EUserCommentForAdmin> searchByOptions(
            @Param("keyword1") String keyword1,
            @Param("keyword2") String keyword2,
            @Param("keyword3") String keyword3,
            @Param("timestampFrom") LocalDateTime timestampFrom,
            @Param("timestampTo") LocalDateTime timestampTo,
            @Param("limit") int limit,
            @Param("offset") long offset,
            @Param("minReportCount") int minReportCount,
            @Param("minUnhandledReportCount") int minUnhandledReportCount
    );

    @Query(value = "" +
            "Select count(id) From " +
            "(" +
            "  Select  " +
            "    c.id,   " +
            "    count(ra.id) as report_count," +
            "    count(rb.id) as unhandled_report_count  " +
            "  From " +
            "    e_user_comment c  " +
            "  Left Join " +
            "    e_user_comment_report ra  " +
            "  On  " +
            "    ra.comment_serial_number = c.id  " +
            "  Left Join " +
            "    e_user_comment_report rb  " +
            "  On  " +
            "    rb.comment_serial_number = c.id  And rb.is_handled = '0'" +
            "  Where  " +
            "   (" +
            "     c.comment_text like Concat('%', :keyword1, '%') " +
            "   OR " +
            "     c.handle_name like Concat('%', :keyword1, '%') " +
            "   OR :keyword1 is null " +
            "   )" +
            "   And " +
            "   (" +
            "     c.comment_text like Concat('%', :keyword2, '%') " +
            "   OR " +
            "     c.handle_name like Concat('%', :keyword2, '%') " +
            "   OR :keyword2 is null " +
            "   )" +
            "   And " +
            "   (" +
            "     c.comment_text like Concat('%', :keyword3, '%') " +
            "   OR " +
            "     c.handle_name like Concat('%', :keyword3, '%') " +
            "   OR :keyword3 is null " +
            "   )" +
            "   And  " +
            "   (" +
            "     c.update_timestamp between :timestampFrom And :timestampTo " +
            "   OR " +
            "     c.insert_timestamp between :timestampFrom And :timestampTo " +
            "   ) " +
            "  Group By c.id  " +
            "  Having  " +
            "    :minReportCount <= report_count  " +
            "  And  " +
            "    :minUnhandledReportCount <= unhandled_report_count  " +
            ") t", nativeQuery = true)
    long countByOptions(
            @Param("keyword1") String keyword1,
            @Param("keyword2") String keyword2,
            @Param("keyword3") String keyword3,
            @Param("timestampFrom") LocalDateTime timestampFrom,
            @Param("timestampTo") LocalDateTime timestampTo,
            @Param("minReportCount") int minReportCount,
            @Param("minUnhandledReportCount") int minUnhandledReportCount
    );
}
