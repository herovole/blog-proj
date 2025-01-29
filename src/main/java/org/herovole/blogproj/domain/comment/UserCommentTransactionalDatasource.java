package org.herovole.blogproj.domain.comment;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.rating.RatingLog;
import org.herovole.blogproj.domain.comment.reporting.Reporting;

public interface UserCommentTransactionalDatasource {

    int amountOfCachedTransactions();

    void flush(AppSession session);

    void insert(CommentUnit commentUnit);

    void insertRating(RatingLog ratingLog);

    void updateRating(RatingLog before, RatingLog after);

    void insertReport(Reporting report);

    void hides(IntegerId commentSerialNumber, boolean hides);

}
