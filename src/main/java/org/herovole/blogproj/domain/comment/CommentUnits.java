package org.herovole.blogproj.domain.comment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.FormContents;

import java.util.Arrays;
import java.util.stream.Stream;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentUnits {

    private static final String API_KEY_ORIGINAL_COMMENTS = "originalComments";
    private static final String API_KEY_USER_COMMENTS = "userComments";

    public static CommentUnits fromFormContentToSourceComments(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_ORIGINAL_COMMENTS);
        FormContents arrayChildren = child.getInArray();
        CommentUnit[] comments = arrayChildren.stream().map(CommentUnit::fromFormContentToSourceComment).filter(e -> !e.isEmpty()).toArray(CommentUnit[]::new);
        return of(comments);
    }

    public static CommentUnits fromFormContentToUserComments(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_USER_COMMENTS);
        FormContents arrayChildren = child.getInArray();
        CommentUnit[] comments = arrayChildren.stream().map(CommentUnit::fromFormContentToUserComment).filter(e -> !e.isEmpty()).toArray(CommentUnit[]::new);
        return of(comments);
    }

    public static CommentUnits of(CommentUnit[] units) {
        return new CommentUnits(units);
    }

    public static CommentUnits empty() {
        return of(new CommentUnit[0]);
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

    public CommentUnits maskPrivateItems() {
        return CommentUnits.of(this.stream().map(CommentUnit::maskPrivateItems).toArray(CommentUnit[]::new));
    }

    public CommentUnits filterOutHiddenComments() {
        return CommentUnits.of(this.stream().filter(e -> !e.isHidden()).toArray(CommentUnit[]::new));
    }

    public CommentUnit.Json[] toJson() {
        return stream().map(CommentUnit::toJson).toArray(CommentUnit.Json[]::new);
    }
}
