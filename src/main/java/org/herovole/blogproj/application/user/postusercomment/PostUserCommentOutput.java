package org.herovole.blogproj.application.user.postusercomment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PostUserCommentOutput {
    boolean isValid;

    Json toJson() {
        return new Json(isValid);
    }

    @RequiredArgsConstructor
    record Json(
            boolean isValid
    ) {
    }
}

