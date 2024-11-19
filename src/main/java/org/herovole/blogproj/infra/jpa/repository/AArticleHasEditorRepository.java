package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.AArticleHasEditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AArticleHasEditorRepository extends JpaRepository<AArticleHasEditor, Long> {

    @Query(value = "Select * from a_article_has_editor limit 1", nativeQuery = true)
    AArticleHasEditor findOne();

    @Query(value = "Select * from a_article_has_editor " +
            "where delete_flag = 0 " +
            "  and article_id = :article_id " +
            "order by comment_id",
            nativeQuery = true)
    List<AArticleHasEditor> findByArticleId(@Param("article_id") long articleId);
}
