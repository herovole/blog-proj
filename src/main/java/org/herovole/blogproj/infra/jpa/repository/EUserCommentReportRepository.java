package org.herovole.blogproj.infra.jpa.repository;

import org.herovole.blogproj.infra.jpa.entity.EUserCommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EUserCommentReportRepository extends JpaRepository<EUserCommentReport, Long> {

    @Query(value = "Select * from e_user_comment_report limit 1", nativeQuery = true)
    EUserCommentReport findOne();

    @Query(value = "Select * from e_user_comment_report Where id = :reportId", nativeQuery = true)
    EUserCommentReport findByReportId(@Param("reportId") long reportId);

    @Query(value = "Select " +
            "    r.*, " +
            "    u.banned_until as user_banned_until,  " +
            "    i.banned_until as ip_banned_until  " +
            "  From " +
            "    e_user_comment_report r  " +
            "  Left Join " +
            "    e_public_user u  " +
            "  On  " +
            "    r.reporter_user_id = u.id  " +
            "  Left Join " +
            "    e_public_ip i  " +
            "  On  " +
            "    r.aton = i.aton  " +
            "  Where  " +
            "    r.comment_serial_number = :commentSerialNumber  " +
            "  Order By  " +
            "    r.update_timestamp DESC  " +
            "", nativeQuery = true)
    List<EUserCommentReport.EUserCommentReportForAdmin> findByCommentSerialNumber(
            @Param("commentSerialNumber") long commentSerialNumber);
}
