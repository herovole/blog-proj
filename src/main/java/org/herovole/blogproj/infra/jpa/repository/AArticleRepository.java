package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.AArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AArticleRepository extends JpaRepository<AArticle, Long> {

    @Query(value = "Select * from a_article limit 1", nativeQuery = true)
    AArticle findOne();

}
