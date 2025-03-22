package org.herovole.blogproj.application.user.searchcomments;

import lombok.Builder;
import org.herovole.blogproj.domain.comment.CommentUnit;
import org.herovole.blogproj.domain.comment.CommentUnits;

@Builder
public class SearchUserCommentsOutput {
    private final CommentUnits commentUnits;
    private final long total;

    public Json toJsonModel() {
        return new Json(commentUnits.toJsonModel(), total);
    }

    public record Json(CommentUnit.Json[] commentUnits,
                       long total) {
    }
}
