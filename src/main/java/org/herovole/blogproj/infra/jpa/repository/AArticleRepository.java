package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.AArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface AArticleRepository extends JpaRepository<AArticle, Long> {

    @Query(value = "Select * from a_article limit 1", nativeQuery = true)
    AArticle findOne();

    @Query(value = "Select max(id) from a_article limit 1", nativeQuery = true)
    Long findMaxId();

    @Query(value = "Select id from a_article " +
            " Where " +
            "   (" +
            "     title like Concat('%', :keyword1, '%') " +
            "   OR " +
            "     text like Concat('%', :keyword1, '%') " +
            "   OR :keyword1 is null " +
            "   )" +
            "   And " +
            "   (" +
            "     title like Concat('%', :keyword2, '%') " +
            "   OR " +
            "     text like Concat('%', :keyword2, '%') " +
            "   OR :keyword2 is null " +
            "   )" +
            "   And " +
            "   (" +
            "     title like Concat('%', :keyword3, '%') " +
            "   OR " +
            "     text like Concat('%', :keyword3, '%') " +
            "   OR :keyword3 is null " +
            "   )" +
            "  AND " +
            "   (" +
            "     update_timestamp between :timestampFrom And :timestampTo " +
            "   OR " +
            "     insert_timestamp between :timestampFrom And :timestampTo " +
            "   OR " +
            "     coalesce(source_date,curdate()) between :dateFrom And :dateTo " +
            "    ) " +
            " order by id desc" +
            " limit :limit offset :offset", nativeQuery = true)
    long[] searchByOptions(@Param("keyword1") String keyword1,
                           @Param("keyword2") String keyword2,
                           @Param("keyword3") String keyword3,
                           @Param("timestampFrom") LocalDateTime timestampFrom,
                           @Param("timestampTo") LocalDateTime timestampTo,
                           @Param("dateFrom") LocalDate dateFrom,
                           @Param("dateTo") LocalDate dateTo,
                           @Param("limit") int limit,
                           @Param("offset") long offset
    );

    @Query(value = "Select count(id) from a_article " +
            " Where " +
            "   (" +
            "     title like Concat('%', :keyword1, '%') " +
            "   OR " +
            "     text like Concat('%', :keyword1, '%') " +
            "   OR :keyword1 is null " +
            "   )" +
            "   And " +
            "   (" +
            "     title like Concat('%', :keyword2, '%') " +
            "   OR " +
            "     text like Concat('%', :keyword2, '%') " +
            "   OR :keyword2 is null " +
            "   )" +
            "   And " +
            "   (" +
            "     title like Concat('%', :keyword3, '%') " +
            "   OR " +
            "     text like Concat('%', :keyword3, '%') " +
            "   OR :keyword3 is null " +
            "   )" +
            "  AND " +
            "   (" +
            "     update_timestamp between :timestampFrom And :timestampTo " +
            "   OR " +
            "     insert_timestamp between :timestampFrom And :timestampTo " +
            "   OR " +
            "     coalesce(source_date,curdate()) between :dateFrom And :dateTo " +
            "    ) ", nativeQuery = true)
    long countByOptions(@Param("keyword1") String keyword1,
                        @Param("keyword2") String keyword2,
                        @Param("keyword3") String keyword3,
                        @Param("timestampFrom") LocalDateTime timestampFrom,
                        @Param("timestampTo") LocalDateTime timestampTo,
                        @Param("dateFrom") LocalDate dateFrom,
                        @Param("dateTo") LocalDate dateTo
    );
}
