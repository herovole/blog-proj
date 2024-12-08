package org.herovole.blogproj.domain.comment;


import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.FormContent;

public interface CommentUnit {

    static CommentUnit fromPostContent(FormContent formContent) {
        if (formContent == null) return empty();
        return RealCommentUnit.fromPostContent(formContent);
    }

    static CommentUnit empty() {
        return new EmptyCommentUnit();
    }

    boolean isEmpty();

    IntegerId getCommentId();

    boolean hasSameCommentId(CommentUnit that);

    boolean hasSameContent(CommentUnit that);

    CommentUnit.Json toJson();

    interface Json {
    }
}
