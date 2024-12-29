package org.herovole.blogproj.application.user.rateusercomment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RateUserCommentOutput {

    public static RateUserCommentOutput of(boolean isValid) {
        return new RateUserCommentOutput(isValid);
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

