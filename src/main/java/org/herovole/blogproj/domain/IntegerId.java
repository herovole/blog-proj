package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegerId implements Comparable<IntegerId> {

    private static final String EMPTY = "-";
    private static final String API_KEY_ARTICLE_ID = "id";
    private static final String API_KEY_COMMENT_ID = "commentId";

    public static IntegerId fromPostContentArticleId(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_ARTICLE_ID);
        return valueOf(child.getValue());
    }

    public static IntegerId fromPostContentCommentId(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_COMMENT_ID);
        return valueOf(child.getValue());
    }

    public static IntegerId valueOf(Integer field) {
        if (null == field) return empty();
        return valueOf(Long.valueOf(field));
    }

    public static IntegerId valueOf(Long field) {
        if (null == field) return empty();
        if (field < 0) throw new DomainInstanceGenerationException();
        return new IntegerId(field);
    }

    public static IntegerId valueOf(String field) {
        if (null == field) return empty();
        return new IntegerId(Long.parseLong(field, 10));
    }

    public static IntegerId empty() {
        return new IntegerId(null);
    }

    private final Long id;

    public boolean isEmpty() {
        return null == id;
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : String.valueOf(this.id);
    }

    public Long longMemorySignature() {
        return this.id;
    }

    public Integer intMemorySignature() {
        return Math.toIntExact(this.id);
    }


    @Override
    public int compareTo(IntegerId o) {
        Long thisValue = this.isEmpty() ? -1 : this.id;
        Long thatValue = o.isEmpty() ? -1 : o.id;
        return thisValue.compareTo(thatValue);
    }
}
