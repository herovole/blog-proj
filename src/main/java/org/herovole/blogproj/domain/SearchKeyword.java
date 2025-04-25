package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchKeyword {

    public static SearchKeyword valueOf(String keyword) {
        if (MAX_LENGTH < keyword.length()) {
            throw new DomainInstanceGenerationException(keyword);
        }
        return new SearchKeyword(keyword);
    }

    public static SearchKeyword empty() {
        return new SearchKeyword(null);
    }

    private static final int MAX_LENGTH = 15;

    private final String keyword;

    public boolean isEmpty() {
        return keyword == null || keyword.isEmpty() || keyword.isBlank();
    }

    public String letterSignature() {
        return this.isEmpty() ? "" : this.keyword;
    }

    public String memorySignature() {
        return this.isEmpty() ? null : this.keyword;
    }
}
