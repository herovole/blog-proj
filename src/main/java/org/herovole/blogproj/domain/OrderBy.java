package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderBy {
    NONE("none"),
    ID("id"),
    REGISTRATION_TIMESTAMP("registration"),
    LATEST_COMMENT_TIMESTAMP("comment"),
    ;

    private final String signature;
    private static final Map<String, OrderBy> toEnum = new HashMap<>();
    private static final String API_KEY_ORDER_BY = "orderBy";

    static {
        for (OrderBy orderBy : values()) {
            toEnum.put(orderBy.signature, orderBy);
        }
    }

    public static OrderBy fromFormContent(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_ORDER_BY);
        return of(child.getValue());
    }

    public static OrderBy of(String signature) {
        return toEnum.getOrDefault(signature, NONE);
    }

}
