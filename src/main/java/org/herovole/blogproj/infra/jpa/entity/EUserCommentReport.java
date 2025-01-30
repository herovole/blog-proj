package org.herovole.blogproj.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentText;
import org.herovole.blogproj.domain.comment.reporting.RealReporting;
import org.herovole.blogproj.domain.comment.reporting.Reporting;
import org.herovole.blogproj.domain.comment.reporting.ReportingWithUserBannedUntil;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;

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
        entity.setHandled(report1.getIsHandled().isTrue());
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

    @Column(name = "is_handled")
    private boolean isHandled;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @CreationTimestamp
    @Column(name = "insert_timestamp", updatable = false)
    private LocalDateTime insertTimestamp;

    @Column(name = "delete_flag")
    private boolean deleteFlag;

    public Reporting toDomainObj() {
        return RealReporting.builder()
                .logId(IntegerId.valueOf(getId()))
                .commentSerialNumber(IntegerId.valueOf(getCommentSerialNumber()))
                .publicUserId(IntegerPublicUserId.valueOf(getReporterUserId()))
                .ip(IPv4Address.valueOf(getAton()))
                .reportingText(CommentText.valueOf(getReportText()))
                .isHandled(GenericSwitch.valueOf(isHandled))
                .reportTimestamp(Timestamp.valueOf(getInsertTimestamp()))
                .build();
    }

    public interface EUserCommentReportForAdmin {
        @Value("#{target.id}")
        long getId();

        @Value("#{target.comment_serial_number}")
        long getCommentSerialNumber();

        @Value("#{target.reporter_user_id}")
        long getReporterUserId();

        @Value("#{target.user_banned_until}")
        LocalDateTime getUserBannedUntil();

        @Value("#{target.aton}")
        long getAton();

        @Value("#{target.ip_banned_until}")
        LocalDateTime getIpBannedUntil();

        @Value("#{target.report_text}")
        String getReportText();

        @Value("#{target.is_handled}")
        boolean getIsHandled();

        @Value("#{target.update_timestamp}")
        LocalDateTime getUpdateTimestamp();

        @Value("#{target.insert_timestamp}")
        LocalDateTime getInsertTimestamp();

        @Value("#{target.delete_flag}")
        boolean getDeleteFlag();

        default Reporting toDomainObj() {
            return ReportingWithUserBannedUntil.builder()
                    .reporting(RealReporting.builder()
                            .logId(IntegerId.valueOf(getId()))
                            .commentSerialNumber(IntegerId.valueOf(getCommentSerialNumber()))
                            .publicUserId(IntegerPublicUserId.valueOf(getReporterUserId()))
                            .ip(IPv4Address.valueOf(getAton()))
                            .reportingText(CommentText.valueOf(getReportText()))
                            .isHandled(GenericSwitch.valueOf(getIsHandled()))
                            .reportTimestamp(Timestamp.valueOf(getInsertTimestamp()))
                            .build())
                    .userBannedUntil(Timestamp.valueOf(getUserBannedUntil()))
                    .ipBannedUntil(Timestamp.valueOf(getIpBannedUntil()))
                    .build();
        }
    }

}
