package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.comment.reporting.RealReporting;
import org.herovole.blogproj.domain.comment.reporting.Reporting;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "e_user_comment_report")
@Data
public class EUserCommentReport implements Serializable {
    public static EUserCommentReport fromDomainObjectInsert(Reporting report) {
        RealReporting report1 = (RealReporting) report;

        EUserCommentReport entity = new EUserCommentReport();
        entity.setCommentSerialNumber(report1.getCommentSerialNumber().longMemorySignature());
        entity.setReporterUserId(report1.getPublicUserId().longMemorySignature());
        entity.setAton(report1.getIp().aton());
        entity.setReportText(report1.getReportingText().memorySignature());
        entity.setDeleteFlag(false);
        return entity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @EqualsAndHashCode.Include
    @Column(name = "comment_serial_number")
    private long commentSerialNumber;

    @Column(name = "reporter_user_id")
    private long reporterUserId;

    @Column(name = "aton")
    private long aton;

    @Column(name = "report_text")
    private String reportText;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @CreationTimestamp
    @Column(name = "insert_timestamp", updatable = false)
    private LocalDateTime insertTimestamp;

    @Column(name = "delete_flag")
    private boolean deleteFlag;

}
