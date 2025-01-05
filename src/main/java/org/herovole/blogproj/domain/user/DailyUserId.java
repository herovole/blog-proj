package org.herovole.blogproj.domain.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyUserId {

    private static final int LENGTH = 9;

    public static DailyUserId empty() {
        return new DailyUserId(null);
    }

    public static DailyUserId valueOf(String id) {
        if (id == null || id.isEmpty()) return empty();
        if (id.length() != LENGTH) throw new IllegalArgumentException(id);
        return new DailyUserId(id);
    }

    private final String id;

    public String memorySignature() {
        return id;
    }
}
