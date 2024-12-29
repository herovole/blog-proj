package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.domain.comment.rating.RatingLog;
import org.herovole.blogproj.domain.time.Date;
import org.herovole.blogproj.domain.user.IntegerPublicUserId;
import org.herovole.blogproj.infra.jpa.entity.EUserCommentRating;
import org.herovole.blogproj.infra.jpa.repository.EUserCommentRatingRepository;
import org.herovole.blogproj.infra.jpa.repository.EUserCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userCommentDatasource")
public class UserCommentDatasourceMySql implements UserCommentDatasource {
    protected final EUserCommentRepository eUserCommentRepository;
    protected final EUserCommentRatingRepository eUserCommentRatingRepository;

    @Autowired
    public UserCommentDatasourceMySql(EUserCommentRepository eUserCommentRepository,
                                      EUserCommentRatingRepository eUserCommentRatingRepository) {
        this.eUserCommentRepository = eUserCommentRepository;
        this.eUserCommentRatingRepository = eUserCommentRatingRepository;
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
}
