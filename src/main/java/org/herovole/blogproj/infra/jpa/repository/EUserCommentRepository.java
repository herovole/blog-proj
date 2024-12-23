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

    @Query(value = "Select * " +
            "From e_user_comment " +
            "Where " +
            "  article_id = :articleId" +
            "Order By comment_id", nativeQuery = true)
    List<EUserComment> findByArticleId(@Param("articleId") long articleId);

    @Query(value = "Select COUNT(*) From e_user_comment Where article_id = :articleId", nativeQuery = true)
    int countByArticleId(@Param("articleId") long articleId);

}
