package org.herovole.blogproj.application.user.postusercomment;

public record PostUserCommentDurationConfig(
        int secondsIntervalSameArticle,
        int secondsIntervalGeneralArticle
) {
}
