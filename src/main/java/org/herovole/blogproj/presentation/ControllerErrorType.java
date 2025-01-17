package org.herovole.blogproj.presentation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ControllerErrorType {

    NONE(UseCaseErrorType.NONE, HttpStatus.OK, "Successful."),
    SERVER_ERROR(UseCaseErrorType.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error."),
    GENERIC_USER_ERROR(UseCaseErrorType.GENERIC_USER_ERROR, HttpStatus.BAD_REQUEST, "Bad Request."),
    HUMAN_THREATENING_PHRASE(UseCaseErrorType.HUMAN_THREATENING_PHRASE, HttpStatus.FORBIDDEN, "Inappropriate Phrases (Profanity)."),
    SYSTEM_THREATENING_PHRASE(UseCaseErrorType.SYSTEM_THREATENING_PHRASE, HttpStatus.FORBIDDEN, "Inappropriate Phrases (Attack)."),
    BOT(UseCaseErrorType.BOT, HttpStatus.FORBIDDEN, "Potential Bot Activity."),
    BAN(UseCaseErrorType.BAN, HttpStatus.FORBIDDEN, "Banned."),
    AUTH_FAILURE(UseCaseErrorType.AUTH_FAILURE, HttpStatus.UNAUTHORIZED, "No Valid Token.");

    private static final Map<UseCaseErrorType, ControllerErrorType> toEnum = new HashMap<>();

    static {
        for (ControllerErrorType type : values()) {
            toEnum.put(type.useCaseErrorType, type);
        }
    }

    public static ControllerErrorType of(UseCaseErrorType useCaseErrorType) {
        return toEnum.getOrDefault(useCaseErrorType, NONE);
    }

    private final UseCaseErrorType useCaseErrorType;
    private final HttpStatus httpStatus;
    private final String defaultBrowserDebugMessage;

    public String getAppErrorCode() {
        return this.useCaseErrorType.memorySignature();
    }

    public ResponseEntity<String> buildResponseEntity() {
        return ResponseEntity.status(httpStatus).body(defaultBrowserDebugMessage);
    }

}
