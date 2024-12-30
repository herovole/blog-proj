package org.herovole.blogproj.application.user.reportusercomment.proper;

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

}

