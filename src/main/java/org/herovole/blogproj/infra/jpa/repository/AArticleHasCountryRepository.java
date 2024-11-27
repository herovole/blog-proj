package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.AArticleHasCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AArticleHasCountryRepository extends JpaRepository<AArticleHasCountry, Long> {

    @Query(value = "Select * from a_article_has_country limit 1", nativeQuery = true)
    AArticleHasCountry findOne();

    @Query(value = "Select * from a_article_has_country " +
            "  where article_id = :article_id " +
            "order by iso_2",
            nativeQuery = true)
    List<AArticleHasCountry> findByArticleId(@Param("article_id") long articleId);
}
