package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class IntegerId implements Comparable<IntegerId> {

    private static final String EMPTY = "-";
    private static final String API_KEY_ARTICLE_ID = "articleId";
    private static final String API_KEY_COMMENT_ID = "commentId";

    public static IntegerId fromFormContentArticleId(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_ARTICLE_ID);
        return valueOf(child.getValue());
    }

    public static IntegerId fromFormContentTopicTagId(FormContent formContent) {
        return fromFormContentArticleId(formContent);
    }

    public static IntegerId fromFormContentCommentId(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_COMMENT_ID);
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

    public static IntegerId valueOf(int field) {
        return valueOf(Long.valueOf(field));
    }

    public static IntegerId valueOf(String field) {
        if (null == field || field.isEmpty() || EMPTY.equals(field)) return empty();
        return new IntegerId(Long.parseLong(field, 10));
    }

    public static IntegerId empty() {
        return new IntegerId(null);
    }

    protected final Long id;

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
        return isEmpty() ? null : Math.toIntExact(this.id);
    }


    @Override
    public int compareTo(IntegerId o) {
        Long thisValue = this.isEmpty() ? -1 : this.id;
        Long thatValue = o.isEmpty() ? -1 : o.id;
        return thisValue.compareTo(thatValue);
    }
}
