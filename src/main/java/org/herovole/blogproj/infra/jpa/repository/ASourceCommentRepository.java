package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.ASourceComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ASourceCommentRepository extends JpaRepository<ASourceComment, Long> {

    @Query(value = "Select * from a_source_comment limit 1", nativeQuery = true)
    ASourceComment findOne();

    @Query(value = "Select * from a_source_comment " +
            "where delete_flag = 0 " +
            "  and article_id = :article_id " +
            "order by comment_id",
            nativeQuery = true)
    List<ASourceComment> findByArticleId(@Param("article_id") long articleId);

    @Query(value = "Select count(id) from a_source_comment " +
            "where delete_flag = 0 " +
            "  and article_id = :article_id " +
            "order by comment_id",
            nativeQuery = true)
    int countByArticleId(@Param("article_id") long articleId);
}
