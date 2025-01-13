package org.herovole.blogproj.domain.comment;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.rating.RatingLog;
import org.herovole.blogproj.domain.time.Date;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;

public interface UserCommentDatasource {

    RatingLog findActiveRatingHistory(IntegerId commentSerialNumber, IntegerPublicUserId userId);

    RatingLog findActiveRatingHistory(IntegerId commentSerialNumber, IPv4Address ip, Date date);
}
