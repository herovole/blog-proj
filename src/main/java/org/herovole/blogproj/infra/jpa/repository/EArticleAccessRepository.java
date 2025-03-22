package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.EArticleAccess;
import org.herovole.blogproj.infra.jpa.entity.EPublicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EArticleAccessRepository extends JpaRepository<EArticleAccess, Long> {

    @Query(value = "Select * from e_article_access limit 1", nativeQuery = true)
    EPublicUser findOne();

}
