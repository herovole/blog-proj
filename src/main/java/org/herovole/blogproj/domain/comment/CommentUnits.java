package org.herovole.blogproj.domain.comment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.PostContents;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentUnits {

    private static final String API_KEY_ORIGINAL_COMMENTS = "originalComments";

    public static CommentUnits fromPostContentEditors(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_ORIGINAL_COMMENTS);
        PostContents arrayChildren = child.getInArray();
        CommentUnit[] comments = arrayChildren.stream().map(CommentUnit::fromPostContent).toArray(CommentUnit[]::new);
        return of(comments);
    }

    public static CommentUnits of(CommentUnit[] units) {
        return new CommentUnits(units);
    }

    private final CommentUnit[] units;
}
