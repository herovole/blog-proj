package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.AArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AArticleRepository extends JpaRepository<AArticle, Long> {

    @Query(value = "Select * from a_article limit 1", nativeQuery = true)
    AArticle findOne();

    @Query(value = "Select max(id) from a_article limit 1", nativeQuery = true)
    Long findMaxId();

    @Query(value = """
            Select
              a.id,
              max(a.registration_timestamp) as registration_timestamp,
              coalesce(max(u.insert_timestamp), max(a.registration_timestamp)) as latest_comment_timestamp
            from a_article a
            Left Join a_article_has_topic_tag t
              On t.article_id = a.id
            Left Join a_article_has_country c
              On c.article_id = a.id
            Left Join e_user_comment u
              On u.article_id = a.id
            Where
              a.is_published = :isPublished
              And
              (
                t.topic_tag_id = :topicTagId1
              OR
                :topicTagId1 is NULL
              )
              And
              (
                c.iso_2 = :countryTagId1
              OR
                :countryTagId1 = '--'
              )
              And
              (
                a.title like Concat('%', :keyword1, '%')
              OR
                a.text like Concat('%', :keyword1, '%')
              OR :keyword1 is null
              )
              And
              (
                a.title like Concat('%', :keyword2, '%')
              OR
                a.text like Concat('%', :keyword2, '%')
              OR :keyword2 is null
              )
              And
              (
                a.title like Concat('%', :keyword3, '%')
              OR
                a.text like Concat('%', :keyword3, '%')
              OR :keyword3 is null
              )
             AND
              (
                a.registration_timestamp between :timestampFrom And :timestampTo
              OR
                coalesce(a.source_date,curdate()) between :dateFrom And :dateTo
               )
            group by a.id
            order by a.id
            limit :limit offset :offset
            """, nativeQuery = true)
    List<AArticle.AArticleSearchIds> searchByOptionsOrderById(@Param("isPublished") int isPublished,
                                                          @Param("topicTagId1") Integer topicTagId1,
                                                          @Param("countryTagId1") String countryTagId1,
                                                          @Param("keyword1") String keyword1,
                                                          @Param("keyword2") String keyword2,
                                                          @Param("keyword3") String keyword3,
                                                          @Param("timestampFrom") LocalDateTime timestampFrom,
                                                          @Param("timestampTo") LocalDateTime timestampTo,
                                                          @Param("dateFrom") LocalDate dateFrom,
                                                          @Param("dateTo") LocalDate dateTo,
                                                          @Param("limit") int limit,
                                                          @Param("offset") long offset
    );

    @Query(value = """
            Select
              a.id,
              max(a.registration_timestamp) as registration_timestamp,
              coalesce(max(u.insert_timestamp), max(a.registration_timestamp)) as latest_comment_timestamp
            from a_article a
            Left Join a_article_has_topic_tag t
              On t.article_id = a.id
            Left Join a_article_has_country c
              On c.article_id = a.id
            Left Join e_user_comment u
              On u.article_id = a.id
            Where
              a.is_published = :isPublished
              And
              (
                t.topic_tag_id = :topicTagId1
              OR
                :topicTagId1 is NULL
              )
              And
              (
                c.iso_2 = :countryTagId1
              OR
                :countryTagId1 = '--'
              )
              And
              (
                a.title like Concat('%', :keyword1, '%')
              OR
                a.text like Concat('%', :keyword1, '%')
              OR :keyword1 is null
              )
              And
              (
                a.title like Concat('%', :keyword2, '%')
              OR
                a.text like Concat('%', :keyword2, '%')
              OR :keyword2 is null
              )
              And
              (
                a.title like Concat('%', :keyword3, '%')
              OR
                a.text like Concat('%', :keyword3, '%')
              OR :keyword3 is null
              )
             AND
              (
                a.registration_timestamp between :timestampFrom And :timestampTo
              OR
                coalesce(a.source_date,curdate()) between :dateFrom And :dateTo
               )
            group by a.id
            order by registration_timestamp
            limit :limit offset :offset
            """, nativeQuery = true)
    List<AArticle.AArticleSearchIds> searchByOptionsOrderByRegistrationTimestamp(@Param("isPublished") int isPublished,
                                                                             @Param("topicTagId1") Integer topicTagId1,
                                                                             @Param("countryTagId1") String countryTagId1,
                                                                             @Param("keyword1") String keyword1,
                                                                             @Param("keyword2") String keyword2,
                                                                             @Param("keyword3") String keyword3,
                                                                             @Param("timestampFrom") LocalDateTime timestampFrom,
                                                                             @Param("timestampTo") LocalDateTime timestampTo,
                                                                             @Param("dateFrom") LocalDate dateFrom,
                                                                             @Param("dateTo") LocalDate dateTo,
                                                                             @Param("limit") int limit,
                                                                             @Param("offset") long offset
    );

    @Query(value = """
            Select
              a.id,
              max(a.registration_timestamp) as registration_timestamp,
              coalesce(max(u.insert_timestamp), max(a.registration_timestamp)) as latest_comment_timestamp
            from a_article a
            Left Join a_article_has_topic_tag t
              On t.article_id = a.id
            Left Join a_article_has_country c
              On c.article_id = a.id
            Left Join e_user_comment u
              On u.article_id = a.id
            Where
              a.is_published = :isPublished
              And
              (
                t.topic_tag_id = :topicTagId1
              OR
                :topicTagId1 is NULL
              )
              And
              (
                c.iso_2 = :countryTagId1
              OR
                :countryTagId1 = '--'
              )
              And
              (
                a.title like Concat('%', :keyword1, '%')
              OR
                a.text like Concat('%', :keyword1, '%')
              OR :keyword1 is null
              )
              And
              (
                a.title like Concat('%', :keyword2, '%')
              OR
                a.text like Concat('%', :keyword2, '%')
              OR :keyword2 is null
              )
              And
              (
                a.title like Concat('%', :keyword3, '%')
              OR
                a.text like Concat('%', :keyword3, '%')
              OR :keyword3 is null
              )
             AND
              (
                a.registration_timestamp between :timestampFrom And :timestampTo
              OR
                coalesce(a.source_date,curdate()) between :dateFrom And :dateTo
               )
            group by a.id
            order by latest_comment_timestamp
            limit :limit offset :offset
            """, nativeQuery = true)
    List<AArticle.AArticleSearchIds> searchByOptionsOrderByLatestCommentTimestamp(@Param("isPublished") int isPublished,
                                                                                  @Param("topicTagId1") Integer topicTagId1,
                                                                                  @Param("countryTagId1") String countryTagId1,
                                                                                  @Param("keyword1") String keyword1,
                                                                                  @Param("keyword2") String keyword2,
                                                                                  @Param("keyword3") String keyword3,
                                                                                  @Param("timestampFrom") LocalDateTime timestampFrom,
                                                                                  @Param("timestampTo") LocalDateTime timestampTo,
                                                                                  @Param("dateFrom") LocalDate dateFrom,
                                                                                  @Param("dateTo") LocalDate dateTo,
                                                                                  @Param("limit") int limit,
                                                                                  @Param("offset") long offset
    );

    @Query(value = "Select count(distinct(a.id)) from a_article a  " +
            " Left Join a_article_has_topic_tag t  " +
            "   On t.article_id = a.id  " +
            " Left Join a_article_has_country c  " +
            "   On c.article_id = a.id  " +
            " Where " +
            "   a.is_published = :isPublished " +
            "   And  " +
            "   (  " +
            "     t.topic_tag_id = :topicTagId1  " +
            "   OR  " +
            "     :topicTagId1 is NULL  " +
            "   )  " +
            "   And  " +
            "   (  " +
            "     c.iso_2 = :countryTagId1  " +
            "   OR  " +
            "     :countryTagId1 = '--' " +
            "   )  " +
            "   And " +
            "   (" +
            "     a.title like Concat('%', :keyword1, '%') " +
            "   OR " +
            "     a.text like Concat('%', :keyword1, '%') " +
            "   OR :keyword1 is null " +
            "   )" +
            "   And " +
            "   (" +
            "     a.title like Concat('%', :keyword2, '%') " +
            "   OR " +
            "     a.text like Concat('%', :keyword2, '%') " +
            "   OR :keyword2 is null " +
            "   )" +
            "   And " +
            "   (" +
            "     a.title like Concat('%', :keyword3, '%') " +
            "   OR " +
            "     a.text like Concat('%', :keyword3, '%') " +
            "   OR :keyword3 is null " +
            "   )" +
            "  AND " +
            "   (" +
            "     a.registration_timestamp between :timestampFrom And :timestampTo " +
            "   OR " +
            "     coalesce(a.source_date,curdate()) between :dateFrom And :dateTo " +
            "    ) ", nativeQuery = true)
    long countByOptions(@Param("isPublished") int isPublished,
                        @Param("topicTagId1") Integer topicTagId1,
                        @Param("countryTagId1") String countryTagId1,
                        @Param("keyword1") String keyword1,
                        @Param("keyword2") String keyword2,
                        @Param("keyword3") String keyword3,
                        @Param("timestampFrom") LocalDateTime timestampFrom,
                        @Param("timestampTo") LocalDateTime timestampTo,
                        @Param("dateFrom") LocalDate dateFrom,
                        @Param("dateTo") LocalDate dateTo
    );
}
