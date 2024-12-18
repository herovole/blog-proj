package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.EUserCommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EUserCommentReportRepository extends JpaRepository<EUserCommentReport, Long> {

    @Query(value = "Select * from e_user_comment_report limit 1", nativeQuery = true)
    EUserCommentReport findOne();

}
