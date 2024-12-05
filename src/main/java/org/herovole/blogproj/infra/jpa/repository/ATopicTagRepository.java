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

    @Query(value = "Select * from a_topic_tag where delete_flag = 0 order by id",
            nativeQuery = true)
    List<ATopicTag> findAllTags();

    @Query(value = "Select * from a_topic_tag Where delete_flag = 0 and id = :id", nativeQuery = true)
    ATopicTag findByTopicTagId(@Param("id") long id);

    @Query(value = "Select * from a_topic_tag Where delete_flag = 0 " +
            " Order By id " +
            " Limit :limit " +
            " Offset :offset ", nativeQuery = true)
    List<ATopicTag> searchByOptions(@Param("limit") int limit, @Param("offset") long offset);

    @Query(value = "Select count(id) from a_topic_tag Where delete_flag = 0 ", nativeQuery = true)
    long countAll();
}
