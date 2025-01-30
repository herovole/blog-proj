package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericSwitch implements Comparable<GenericSwitch> {

    private static final String EMPTY = "-";
    private static final String TRUE1 = "true";
    private static final String TRUE2 = "1";
    private static final String FALSE1 = "false";
    private static final String FALSE2 = "0";
    private static final String API_KEY_IS_PUBLISHED = "isPublished";
    private static final String API_KEY_IS_HIDDEN = "isHidden";
    private static final String API_KEY_REQUIRES_AUTH = "requiresAuth";
    private static final String API_KEY_HAS_REPORTS = "hasReports";
    private static final String API_KEY_HAS_UNHANDLED_REPORTS = "hasUnhandledReports";
    private static final String API_KEY_IS_HANDLED = "isHandled";

    public static GenericSwitch fromFormContentIsPublished(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_IS_PUBLISHED);
        return valueOf(child.getValue());
    }

    public static GenericSwitch fromFormContentIsHidden(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_IS_HIDDEN);
        return valueOf(child.getValue());
    }

    public static GenericSwitch fromFormContentRequiresAuth(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_REQUIRES_AUTH);
        return valueOf(child.getValue());
    }

    public static GenericSwitch fromFormContentHasReports(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_HAS_REPORTS);
        return valueOf(child.getValue());
    }

    public static GenericSwitch fromFormContentHasUnhandledReports(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_HAS_UNHANDLED_REPORTS);
        return valueOf(child.getValue());
    }

    public static GenericSwitch fromFormContentIsHandled(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_IS_HANDLED);
        return valueOf(child.getValue());
    }

    public static GenericSwitch valueOf(String field) {
        if (field == null || field.equalsIgnoreCase(EMPTY)) {
            return empty();
        }
        if (field.equalsIgnoreCase(TRUE1) || field.equalsIgnoreCase(TRUE2)) {
            return positive();
        }
        if (field.equalsIgnoreCase(FALSE1) || field.equalsIgnoreCase(FALSE2)) {
            return negative();
        }
        throw new DomainInstanceGenerationException();
    }

    public static GenericSwitch valueOf(Boolean field) {
        if (field == null) return empty();
        return field ? positive() : negative();
    }

    public static GenericSwitch positive() {
        return new GenericSwitch(true);
    }

    public static GenericSwitch negative() {
        return new GenericSwitch(false);
    }

    public static GenericSwitch empty() {
        return new GenericSwitch(null);
    }

    private final Boolean field;

    public boolean isEmpty() {
        return null == field;
    }

    public boolean isTrue() {
        return !this.isEmpty() && field;
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : String.valueOf(this.field);
    }

    public Boolean memorySignature() {
        return this.field;
    }

    @Override
    public int compareTo(GenericSwitch o) {
        return this.field.compareTo(o.field);
    }
}
