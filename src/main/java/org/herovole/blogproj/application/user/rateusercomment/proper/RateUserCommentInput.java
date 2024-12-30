package org.herovole.blogproj.application.user.rateusercomment.proper;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.rating.Rating;
import org.herovole.blogproj.domain.comment.rating.RatingLog;
import org.herovole.blogproj.domain.comment.rating.RealRatingLog;
import org.herovole.blogproj.domain.user.IntegerPublicUserId;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RateUserCommentInput {

    private final IPv4Address iPv4Address;
    private final IntegerPublicUserId userId;

    private final IntegerId commentSerialNumber;
    private final Rating rating;

    RatingLog buildRatingLog() {
        return RealRatingLog.builder()
                .logId(IntegerId.empty())
                .commentSerialNumber(commentSerialNumber)
                .publicUserId(userId)
                .ip(iPv4Address)
                .rating(rating)
                .build();
    }
}

