package org.herovole.blogproj.application.user.postusercomment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUserCommentOutput {

    public static PostUserCommentOutput of(boolean isValid) {
        return new PostUserCommentOutput(isValid);
    }

    private final boolean isValid;

    Json toJson() {
        return new Json(isValid);
    }

    record Json(
            boolean isValid
    ) {
    }
}

