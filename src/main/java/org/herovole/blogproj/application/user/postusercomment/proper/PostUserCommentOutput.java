package org.herovole.blogproj.application.user.postusercomment.proper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUserCommentOutput {

    public static PostUserCommentOutput of(boolean hasValidContent) {
        return new PostUserCommentOutput(hasValidContent);
    }

    private final boolean hasValidContent;

    public boolean hasValidContent() {
        return hasValidContent;
    }

}

