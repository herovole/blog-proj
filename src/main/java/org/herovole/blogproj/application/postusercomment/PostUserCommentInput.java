package org.herovole.blogproj.application.postusercomment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.CommentText;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IntegerId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUserCommentInput {

    private static final String API_KEY_PREFIX = "userComment";

    public static PostUserCommentInput fromFormContent(FormContent formContent) {
        FormContent children = formContent.getChildren(API_KEY_PREFIX);
        return new PostUserCommentInput(
                IntegerId.fromFormContentArticleId(children),
                CommentText.fromFormContentCommentText(children)
        );
    }

    private final IntegerId articleId;
    private final CommentText commentText;
}
