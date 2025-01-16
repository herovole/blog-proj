package org.herovole.blogproj.application.user.postusercomment;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.application.FilteringResult;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUserCommentOutput {
    public static PostUserCommentOutput of(boolean hasValidContent) {
        return new PostUserCommentOutput(hasValidContent, FilteringResult.passed());
    }

    private final boolean hasValidContent;
    private final FilteringResult filteringResult;

    public Json toJsonModel() {
        return new Json(hasValidContent, filteringResult.toJsonModel());
    }

    public record Json(boolean hasValidContent, FilteringResult.Json filteringResult) {

        public String toJsonString() {
            return new Gson().toJson(this);
        }
    }

}

