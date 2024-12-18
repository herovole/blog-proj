package org.herovole.blogproj.domain.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyUserId {

    public static DailyUserId empty() {
        return valueOf(null);
    }

    public static DailyUserId valueOf(String id) {
        return new DailyUserId(id);
    }

    private final String id;

    public String memorySignature() {
        return id;
    }
}
