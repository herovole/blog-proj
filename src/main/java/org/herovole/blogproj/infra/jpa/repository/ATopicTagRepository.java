package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.ATopicTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ATopicTagRepository extends JpaRepository<ATopicTag, Long> {

    @Query(value = "Select * from a_topic_tag limit 1", nativeQuery = true)
    ATopicTag findOne();

    @Query(value = "Select max(id) from a_topic_tag", nativeQuery = true)
    int findMaxId();

    @Query(value = "Select * from a_topic_tag where delete_flag = 0 order by id",
            nativeQuery = true)
    List<ATopicTag> findAllTags();

    @Query(value = "Select * from a_topic_tag Where delete_flag = 0 and id = :id", nativeQuery = true)
    ATopicTag findByTopicTagId(@Param("id") long id);

    @Query(value = "Select t.* from " +
            "  a_topic_tag t " +
            " Where " +
            "  t.delete_flag = 0 " +
            " Order By t.id " +
            " Limit :limit " +
            " Offset :offset ", nativeQuery = true)
    List<ATopicTag> searchByOptions(@Param("limit") int limit, @Param("offset") long offset);

    @Query(value = "Select t.*, count(h.article_id) as article_count from " +
            "   a_topic_tag t " +
            " Left Join " +
            "   a_article_has_topic_tag h " +
            " On " +
            "   h.topic_tag_id = t.id " +
            " Where " +
            "   t.delete_flag = 0 " +
            " Group By t.id " +
            " Order By t.id " +
            " Limit :limit " +
            " Offset :offset ", nativeQuery = true)
    List<ATopicTag.ATopicTagWithStat> searchByOptionsWithStat(@Param("limit") int limit, @Param("offset") long offset);

    @Query(value = "Select count(id) from a_topic_tag Where delete_flag = 0 ", nativeQuery = true)
    long countAll();
}
