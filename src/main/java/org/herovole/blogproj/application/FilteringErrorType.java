package org.herovole.blogproj.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum FilteringErrorType {
    NONE("NNE"),
    SERVER_ERROR("SVR"),
    THREATENING_PHRASE("THR"),
    BOT("BOT"),
    BAN("BAN"),
    AUTH_FAILURE("ATH");
    private final String code;

    public String memorySignature() {
        return code;
    }
}
