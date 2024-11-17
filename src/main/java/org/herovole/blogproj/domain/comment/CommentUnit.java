package org.herovole.blogproj.domain.comment;


import org.herovole.blogproj.domain.PostContent;

public interface CommentUnit {

    static CommentUnit fromPostContent(PostContent postContent) {
        if (postContent == null) return empty();
        return RealCommentUnit.fromPostContent(postContent);
    }

    static CommentUnit empty() {
        return new EmptyCommentUnit();
    }

    boolean isEmpty();
}
