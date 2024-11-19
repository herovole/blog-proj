package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.AArticleHasTopicTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AArticleHasTopicTagRepository extends JpaRepository<AArticleHasTopicTag, Long> {

    @Query(value = "Select * from a_article_has_topic_tag limit 1", nativeQuery = true)
    AArticleHasTopicTag findOne();

    @Query(value = "Select * from a_article_has_topic_tag " +
            "where delete_flag = 0 " +
            "  and article_id = :article_id " +
            "order by comment_id",
            nativeQuery = true)
    List<AArticleHasTopicTag> findByArticleId(@Param("article_id") long articleId);
}
