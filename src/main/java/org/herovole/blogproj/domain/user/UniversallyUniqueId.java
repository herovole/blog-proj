package org.herovole.blogproj.domain.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.regex.Pattern;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UniversallyUniqueId {

    private static final Pattern FORMAT = Pattern.compile("[\\da-f]{8}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{12}");

    public static UniversallyUniqueId valueOf(String id) {
        if (FORMAT.matcher(id).matches())
            return new UniversallyUniqueId(id);
        throw new IllegalArgumentException("Invalid UniversaryUniqueId format" + id);
    }

    public static UniversallyUniqueId empty() {
        return new UniversallyUniqueId(null);
    }

    public static UniversallyUniqueId generate() {
        String userId = UUID.randomUUID().toString();
        return valueOf(userId);
    }

    private final String id;

    public boolean isEmpty() {
        return id == null;
    }

    public String letterSignature() {
        return this.isEmpty() ? "" : this.id;
    }
}
