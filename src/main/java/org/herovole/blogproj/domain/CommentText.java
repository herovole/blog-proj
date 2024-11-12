package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentText {

    private static final String API_KEY_COMMENT_TEXT = "text";
    private static final String EMPTY = "-";

    public static CommentText fromPostContentCommentText(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_COMMENT_TEXT);
        return valueOf(child.getValue());
    }

    public static CommentText valueOf(String text) {
        return new CommentText(text);
    }

    public static CommentText empty() {
        return new CommentText("");
    }

    private final String text;

    public boolean isEmpty() {
        return null == text || text.isEmpty();
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : this.text;
    }

    public String memorySignature() {
        return this.isEmpty() ? "" : this.text;
    }

}
