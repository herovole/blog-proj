package org.herovole.blogproj.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UseCaseErrorType {
    NONE("NNE"),
    SERVER_ERROR("SVR"),
    GENERIC_USER_ERROR("GUE"),
    HUMAN_THREATENING_PHRASE("HTH"),
    SYSTEM_THREATENING_PHRASE("STH"),
    BOT("BOT"),
    BAN("BAN"),
    AUTH_FAILURE("ATH");
    private final String code;

    public String memorySignature() {
        return code;
    }
}
