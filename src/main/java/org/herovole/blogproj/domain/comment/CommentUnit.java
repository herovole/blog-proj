package org.herovole.blogproj.domain.comment;


import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.user.DailyUserIdFactory;

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

    IntegerId getCommentId();

    IntegerId getArticleId();

    HandleName getHandleName();

    CommentText getCommentText();

    boolean hasSameCommentId(CommentUnit that);

    boolean hasSameContent(CommentUnit that);

    CommentUnit appendDailyUserId(DailyUserIdFactory algorithm) throws NoSuchAlgorithmException;

    CommentUnit maskPrivateItems();

    boolean isHidden();

    CommentUnit.Json toJson();

    interface Json {
    }
}
