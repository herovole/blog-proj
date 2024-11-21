package org.herovole.blogproj.domain.comment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.PostContents;

import java.util.Arrays;
import java.util.stream.Stream;

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

    public Stream<CommentUnit> stream() {
        return Arrays.stream(units);
    }

    public boolean hasInId(CommentUnit commentUnit) {
        return this.stream().anyMatch(e -> e.hasSameCommentId(commentUnit));
    }

    private CommentUnit getBySameId(CommentUnit commentUnit) {
        CommentUnit[] commentOfSameId = this.stream().filter(e -> !e.isEmpty() && e.hasSameCommentId(commentUnit)).toArray(CommentUnit[]::new);
        if (commentOfSameId.length == 0) return CommentUnit.empty();
        return commentOfSameId[0];
    }

    public CommentUnits lacksInId(CommentUnits that) {
        return CommentUnits.of(that.stream().filter(e -> !this.hasInId(e)).toArray(CommentUnit[]::new));
    }

    public CommentUnits differ(CommentUnits that) {
        return CommentUnits.of(that.stream().filter(e ->
                        this.hasInId(e) &&
                                !this.getBySameId(e).hasSameContent(e)
                )
                .toArray(CommentUnit[]::new));
    }
}
