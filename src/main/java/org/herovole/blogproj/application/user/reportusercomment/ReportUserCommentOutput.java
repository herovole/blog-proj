package org.herovole.blogproj.application.user.reportusercomment;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportUserCommentOutput {

    public static ReportUserCommentOutput of(boolean hasValidContent) {
        return new ReportUserCommentOutput(hasValidContent);
    }

    private final boolean hasValidContent;

    public boolean hasValidContent() {
        return hasValidContent;
    }

    public Json toJsonModel() {
        return new Json(hasValidContent);
    }

    public record Json(
            boolean hasValidContent
    ) {
        public String toJsonString() {
            return new Gson().toJson(this);
        }
    }
}

