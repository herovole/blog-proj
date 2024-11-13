package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.ATag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ATagRepository extends JpaRepository<ATag, Long> {

    @Query(value = "Select * from a_tag limit 1", nativeQuery = true)
    ATag findOne();

    @Query(value = "Select * from a_tag where delete_flag = 0 order by id",
            nativeQuery = true)
    List<ATag> findAllTags();
}
