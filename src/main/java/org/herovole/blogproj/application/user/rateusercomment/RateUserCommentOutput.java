package org.herovole.blogproj.application.user.rateusercomment;

import com.google.gson.Gson;
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

    public record Json(
            boolean hasValidContent
    ) {
        public String toJsonString() {
            return new Gson().toJson(this);
        }
    }
}

