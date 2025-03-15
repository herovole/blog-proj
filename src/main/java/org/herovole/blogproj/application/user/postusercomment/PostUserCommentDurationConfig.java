package org.herovole.blogproj.application.user.postusercomment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public record PostUserCommentDurationConfig(
        int secondsIntervalSameArticle,
        int secondsIntervalGeneralArticle
) {
}
