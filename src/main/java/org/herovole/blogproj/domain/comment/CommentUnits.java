package org.herovole.blogproj.domain.comment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.FormContents;
import org.herovole.blogproj.domain.IntegerId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentUnits {

    private static final String API_KEY_SOURCE_COMMENTS = "sourceComments";
    private static final String API_KEY_USER_COMMENTS = "userComments";

    public static CommentUnits fromFormContentToSourceComments(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_SOURCE_COMMENTS);
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

    public static CommentUnits of(List<CommentUnit> units) {
        return new CommentUnits(units.toArray(CommentUnit[]::new));
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

    public boolean isEmpty() {
        return this.units.length == 0;
    }

    public boolean hasInId(CommentUnit commentUnit) {
        return this.stream().anyMatch(e -> e.hasSameCommentId(commentUnit));
    }

    private CommentUnit getBySameId(CommentUnit commentUnit) {
        CommentUnit[] commentOfSameId = this.stream().filter(e -> !e.isEmpty() && e.hasSameCommentId(commentUnit)).toArray(CommentUnit[]::new);
        if (commentOfSameId.length == 0) return CommentUnit.empty();
        return commentOfSameId[0];
    }

    public CommentUnits unknownCommentsOf(CommentUnits that) {
        return CommentUnits.of(that.stream().filter(e -> !this.hasInId(e)).toArray(CommentUnit[]::new));
    }

    public CommentUnits differentCommentsOf(CommentUnits that) {
        CommentUnit[] differentUnits = that.stream()
                .filter(e -> !e.getSerialNumber().isEmpty() && this.hasInId(e) && !this.getBySameId(e).hasSameContent(e))
                .toArray(CommentUnit[]::new);
        return CommentUnits.of(differentUnits);
    }

    private CommentUnit findByInArticleCommentId(IntegerId commentId) {
        CommentUnit[] commentOfSameId = this.stream().filter(e -> !e.isEmpty() && e.getCommentId().equals(commentId)).toArray(CommentUnit[]::new);
        if (commentOfSameId.length == 0) return CommentUnit.empty();
        return commentOfSameId[0];
    }

    public CommentUnit findBySerialNumber(IntegerId commentSerialNumber) {
        System.out.println("finding " + commentSerialNumber.longMemorySignature());
        for (CommentUnit e : units) {
            System.out.println(e.getSerialNumber().longMemorySignature());
        }
        CommentUnit[] commentOfSameSerialNumber = this.stream().filter(e -> !e.isEmpty() && e.getSerialNumber().equals(commentSerialNumber)).toArray(CommentUnit[]::new);
        if (commentOfSameSerialNumber.length == 0) return CommentUnit.empty();
        return commentOfSameSerialNumber[0];
    }

    private CommentUnits appendUnit(CommentUnit commentUnit) {
        CommentUnit[] newUnits = new CommentUnit[this.units.length + 1];
        System.arraycopy(this.units, 0, newUnits, 0, this.units.length);
        newUnits[this.units.length] = commentUnit;
        return CommentUnits.of(newUnits);
    }

    private CommentUnits appendUnitBelowReferredId(IntegerId refId, CommentUnit unit) {
        int referredUnitIndex = -1;
        CommentUnit referredUnit = CommentUnit.empty();
        for (int i = 0; i < this.units.length; i++) {
            if (this.units[i].getCommentId().equals(refId)) {
                referredUnitIndex = i;
                referredUnit = this.units[i];
            }
        }
        if (referredUnitIndex < 0) {
            return this;
        }
        List<CommentUnit> newUnits = new ArrayList<>(Arrays.asList(this.units)); // This way breaks the immutability of the list.
        for (int i = referredUnitIndex + 1; i < this.units.length; i++) {
            if (this.units[i].getDepth() <= referredUnit.getDepth()) {
                newUnits.add(i, unit);
                return CommentUnits.of(newUnits);
            }
        }
        newUnits.add(unit);
        return CommentUnits.of(newUnits);
    }

    public CommentUnits sort() {
        CommentUnits newUnits = CommentUnits.empty();
        for (CommentUnit unit : this.units) {
            IntegerId refId = unit.getLatestReferredId();
            if (refId.isEmpty()) {
                CommentUnit unitWithDepth = unit.appendDepth(0);
                newUnits = newUnits.appendUnit(unitWithDepth);
            } else {
                CommentUnit referredUnit = newUnits.findByInArticleCommentId(refId);
                if (referredUnit.isEmpty()) {
                    CommentUnit unitWithDepth = unit.appendDepth(0);
                    newUnits = newUnits.appendUnit(unitWithDepth);
                    continue;
                }
                CommentUnit unitWithDepth = unit.appendDepth(referredUnit.getDepth() + 1);
                newUnits = newUnits.appendUnitBelowReferredId(refId, unitWithDepth);
            }
        }
        return newUnits;
    }

    public CommentUnits maskPrivateItems() {
        return CommentUnits.of(this.stream().map(CommentUnit::maskPrivateItems).toArray(CommentUnit[]::new));
    }

    public CommentUnit.Json[] toJsonModel() {
        return stream().map(CommentUnit::toJson).toArray(CommentUnit.Json[]::new);
    }
}
