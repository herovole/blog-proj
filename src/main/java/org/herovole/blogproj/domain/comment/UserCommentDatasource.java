package org.herovole.blogproj.domain.comment;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.rating.RatingLog;
import org.herovole.blogproj.domain.comment.rating.RatingLogs;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Date;

public interface UserCommentDatasource {

    RatingLog findActiveRatingHistory(IntegerId commentSerialNumber, IntegerPublicUserId userId);

    RatingLogs searchActiveRatingHistoryOfArticle(IntegerId articleId, IntegerPublicUserId userId);

    RatingLog findActiveRatingHistory(IntegerId commentSerialNumber, IPv4Address ip, Date date);

    RatingLogs searchActiveRatingHistoryOfArticle(IntegerId articleId, IPv4Address ip, Date date);

    CommentUnits searchComments(UserCommentsSearchOption searchOption);

    CommentUnit findByCommentSerialNumber(IntegerId commentSerialNumber);

    long countComments(UserCommentsSearchOption searchOption);
}
