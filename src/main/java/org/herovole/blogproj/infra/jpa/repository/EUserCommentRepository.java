package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.EUserComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

}
