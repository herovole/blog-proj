package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.UserCommentsSearchOption;
import org.herovole.blogproj.domain.comment.admincommentunit.AdminCommentUnit;
import org.herovole.blogproj.domain.comment.rating.RatingLog;
import org.herovole.blogproj.domain.comment.rating.RatingLogs;
import org.herovole.blogproj.domain.comment.reporting.Reporting;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Date;
import org.herovole.blogproj.infra.jpa.entity.EUserComment;
import org.herovole.blogproj.infra.jpa.entity.EUserCommentRating;
import org.herovole.blogproj.infra.jpa.entity.EUserCommentReport;
import org.herovole.blogproj.infra.jpa.repository.EUserCommentRatingRepository;
import org.herovole.blogproj.infra.jpa.repository.EUserCommentReportRepository;
import org.herovole.blogproj.infra.jpa.repository.EUserCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("userCommentDatasource")
public class UserCommentDatasourceMySql implements UserCommentDatasource {
    protected final EUserCommentRepository eUserCommentRepository;
    protected final EUserCommentRatingRepository eUserCommentRatingRepository;
    protected final EUserCommentReportRepository eUserCommentReportRepository;

    @Autowired
    public UserCommentDatasourceMySql(EUserCommentRepository eUserCommentRepository,
                                      EUserCommentRatingRepository eUserCommentRatingRepository,
                                      EUserCommentReportRepository eUserCommentReportRepository) {
        this.eUserCommentRepository = eUserCommentRepository;
        this.eUserCommentRatingRepository = eUserCommentRatingRepository;
        this.eUserCommentReportRepository = eUserCommentReportRepository;
    }

    @Override
    public RatingLog findActiveRatingHistory(IntegerId commentSerialNumber, IntegerPublicUserId userId) {
        List<EUserCommentRating> entities = eUserCommentRatingRepository.findActiveHistoryByUserId(
                userId.longMemorySignature(),
                commentSerialNumber.longMemorySignature());
        if (entities.isEmpty()) return RatingLog.empty();
        return entities.get(0).toDomainObj();
    }

    @Override
    public RatingLogs searchActiveRatingHistoryOfArticle(IntegerId articleId, IntegerPublicUserId userId) {
        List<EUserCommentRating> entities = eUserCommentRatingRepository.findActiveHistoryByUserIdAndArticleId(
                userId.longMemorySignature(),
                articleId.longMemorySignature());
        RatingLog[] ratingLogs = entities.stream().map(EUserCommentRating::toDomainObj).toArray(RatingLog[]::new);
        return RatingLogs.of(ratingLogs);
    }

    @Override
    public RatingLog findActiveRatingHistory(IntegerId commentSerialNumber, IPv4Address ip, Date date) {
        List<EUserCommentRating> entities = eUserCommentRatingRepository.findActiveHistoryByIpAndTimestampRange(
                ip.aton(),
                date.beginningTimestampOfDay().toLocalDateTime(),
                date.shift(1).beginningTimestampOfDay().toLocalDateTime(),
                commentSerialNumber.longMemorySignature()
        );
        if (entities.isEmpty()) return RatingLog.empty();
        return entities.get(0).toDomainObj();
    }

    @Override
    public RatingLogs searchActiveRatingHistoryOfArticle(IntegerId articleId, IPv4Address ip, Date date) {
        List<EUserCommentRating> entities = eUserCommentRatingRepository.findActiveHistoryByIpAndTimestampRangeAndArticleId(
                ip.aton(),
                date.beginningTimestampOfDay().toLocalDateTime(),
                date.shift(1).beginningTimestampOfDay().toLocalDateTime(),
                articleId.longMemorySignature());
        RatingLog[] ratingLogs = entities.stream().map(EUserCommentRating::toDomainObj).toArray(RatingLog[]::new);
        return RatingLogs.of(ratingLogs);
    }

    @Override
    public CommentUnits searchComments(UserCommentsSearchOption searchOption) {
        List<CommentUnit> commentUnits = new ArrayList<>();

        List<EUserComment.EUserCommentForAdmin> comments = eUserCommentRepository.searchByOptions(
                searchOption.getKeywords().get(0).memorySignature(),
                searchOption.getKeywords().get(1).memorySignature(),
                searchOption.getKeywords().get(2).memorySignature(),
                searchOption.getDateRange().from().beginningTimestampOfDay().toLocalDateTime(),
                searchOption.getDateRange().to().shift(1).beginningTimestampOfDay().toLocalDateTime(),
                searchOption.getPagingRequest().getLimit(),
                searchOption.getPagingRequest().getOffset(),
                searchOption.getHasReports().isTrue() ? 1 : 0,
                searchOption.getHasUnhandledReports().isTrue() ? 1 : 0
        );
        for (EUserComment.EUserCommentForAdmin comment : comments) {
            List<EUserCommentReport.EUserCommentReportForAdmin> reports = eUserCommentReportRepository.findByCommentSerialNumber(comment.getId());
            Reporting[] reportngUnits = reports.stream().map(EUserCommentReport.EUserCommentReportForAdmin::toDomainObj).toArray(Reporting[]::new);
            AdminCommentUnit commentUnit = comment.toDomainObj().append(reportngUnits);
            commentUnits.add(commentUnit);
        }
        return CommentUnits.of(commentUnits);
    }

    @Override
    public long countComments(UserCommentsSearchOption searchOption) {
        return eUserCommentRepository.countByOptions(
                searchOption.getKeywords().get(0).memorySignature(),
                searchOption.getKeywords().get(1).memorySignature(),
                searchOption.getKeywords().get(2).memorySignature(),
                searchOption.getDateRange().from().beginningTimestampOfDay().toLocalDateTime(),
                searchOption.getDateRange().to().shift(1).beginningTimestampOfDay().toLocalDateTime(),
                searchOption.getHasReports().isTrue() ? 1 : 0,
                searchOption.getHasUnhandledReports().isTrue() ? 1 : 0
        );
    }

    @Override
    public CommentUnit findByCommentSerialNumber(IntegerId commentSerialNumber) {
        if (commentSerialNumber.isEmpty()) throw new IllegalArgumentException();
        EUserComment eUserComment = this.eUserCommentRepository.findBySerialNumber(commentSerialNumber.longMemorySignature());
        if (eUserComment == null) return CommentUnit.empty();
        return eUserComment.toDomainObj();
    }

    @Override
    public Reporting findReportById(IntegerId reportId) {
        if (reportId.isEmpty()) throw new IllegalArgumentException();
        EUserCommentReport entity = this.eUserCommentReportRepository.findByReportId(reportId.longMemorySignature());
        if (entity == null) return Reporting.empty();
        return entity.toDomainObj();
    }

    @Override
    public CommentUnit findLastComment(IntegerPublicUserId publicUserId) {
        if (publicUserId.isEmpty()) throw new IllegalArgumentException();
        EUserComment eUserComment = this.eUserCommentRepository.findLastCommentByPublicUserId(publicUserId.longMemorySignature());
        if (eUserComment == null) return CommentUnit.empty();
        return eUserComment.toDomainObj();
    }

    @Override
    public CommentUnit findLastComment(IntegerPublicUserId publicUserId, IntegerId articleId) {
        if (publicUserId.isEmpty() || articleId.isEmpty()) throw new IllegalArgumentException();
        EUserComment eUserComment = this.eUserCommentRepository.findLastCommentByPublicUserIdAndArticleId(publicUserId.longMemorySignature(), articleId.longMemorySignature());
        if (eUserComment == null) return CommentUnit.empty();
        return eUserComment.toDomainObj();
    }
}
