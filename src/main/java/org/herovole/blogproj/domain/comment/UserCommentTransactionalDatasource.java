package org.herovole.blogproj.domain.comment;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.comment.rating.RatingLog;

public interface UserCommentTransactionalDatasource {

    int amountOfCachedTransactions();

    void flush(AppSession session);

    void insert(CommentUnit commentUnit);

    void insertRating(RatingLog ratingLog);

    void updateRating(RatingLog before, RatingLog after);

}
