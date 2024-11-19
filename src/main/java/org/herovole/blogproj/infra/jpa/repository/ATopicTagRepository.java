package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.ATopicTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ATopicTagRepository extends JpaRepository<ATopicTag, Long> {

    @Query(value = "Select * from a_topic_tag limit 1", nativeQuery = true)
    ATopicTag findOne();

    @Query(value = "Select * from a_topic_tag where delete_flag = 0 order by id",
            nativeQuery = true)
    List<ATopicTag> findAllTags();
}
