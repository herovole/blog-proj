package org.herovole.blogproj.application.user.rateusercomment.proper;

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

}

