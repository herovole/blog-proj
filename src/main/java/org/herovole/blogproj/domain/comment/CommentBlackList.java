package org.herovole.blogproj.domain.comment;

public interface CommentBlackList {
    CommentBlackUnit detect(CommentText text);

    CommentBlackUnit detect(HandleName text);
}
