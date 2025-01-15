package org.herovole.blogproj.application.user.rateusercomment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RateUserCommentOutput {

    public static RateUserCommentOutput of(boolean isValid) {
        return new RateUserCommentOutput(isValid);
    }

    private final boolean hasValidOperation;

    public boolean hasValidOperation() {
        return hasValidOperation;
    }

    public Json toJsonModel() {
        return new Json(hasValidOperation);
    }

    record Json(
            boolean hasValidContent
    ) {
    }
}

