package org.herovole.blogproj.domain.accesskey;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PathTypeAccessKey implements AccessKey {

    private static final String NULL_EXPRESSION = "-";

    public static PathTypeAccessKey valueOf(String path) {
        return new PathTypeAccessKey(path);
    }

    public static PathTypeAccessKey empty() {
        return new PathTypeAccessKey(NULL_EXPRESSION);
    }

    private final String path;

    @Override
    public boolean isEmpty() {
        return path == null || path.isEmpty() || path.equals(NULL_EXPRESSION) || path.equalsIgnoreCase("null");
    }

    @Override
    public String memorySignature() {
        return isEmpty() ? NULL_EXPRESSION : this.path;
    }
}
