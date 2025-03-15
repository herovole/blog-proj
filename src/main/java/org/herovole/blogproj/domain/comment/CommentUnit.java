package org.herovole.blogproj.domain.comment;


import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.publicuser.DailyUserIdFactory;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;
import org.herovole.blogproj.domain.time.Timestamp;

import java.security.NoSuchAlgorithmException;

public interface CommentUnit {

    static CommentUnit fromFormContentToSourceComment(FormContent formContent) {
        if (formContent == null) return empty();
        return RealSourceCommentUnit.fromFormContent(formContent);
    }

    static CommentUnit fromFormContentToUserComment(FormContent formContent) {
        if (formContent == null) return empty();
        return RealUserCommentUnit.fromFormContent(formContent);
    }


    static CommentUnit empty() {
        return new EmptyCommentUnit();
    }

    boolean isEmpty();

    IntegerId getSerialNumber();
    IntegerId getCommentId();

    IntegerId getArticleId();

    HandleName getHandleName();

    CommentText getCommentText();
    IntegerId getLatestReferredId();

    IntegerPublicUserId getPublicUserId();
    Timestamp getPostTimestamp();

    default long getPostedSecondsAgo() {
        if (this.getPostTimestamp().isEmpty()) return Long.MAX_VALUE;
        return Timestamp.now().minusInSeconds(this.getPostTimestamp());
    }

    boolean hasSameCommentId(CommentUnit that);

    boolean hasSameContent(CommentUnit that);

    CommentUnit appendDailyUserId(DailyUserIdFactory algorithm) throws NoSuchAlgorithmException;

    CommentUnit maskPrivateItems();

    int getDepth();
    CommentUnit appendDepth(int depth);

    boolean isHidden();

    CommentUnit.Json toJson();

    interface Json {
        default Json appendDepth(int depth) {
            return this;
        }
    }
}
