package org.herovole.blogproj.application;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.time.Timestamp;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FilteringResult {
    public static FilteringResult passed() {
        return FilteringResult.builder()
                .hasPassed(true)
                .code(FilteringErrorType.NONE)
                .timestampBannedUntil(null)
                .message("")
                .build();
    }

    public static FilteringResult internalServerError() {
        return FilteringResult.builder()
                .hasPassed(false)
                .code(FilteringErrorType.SERVER_ERROR)
                .timestampBannedUntil(null)
                .message("Internal Server Error")
                .build();
    }

    static final String CODE_SERVER_ERROR = "SVR";

    private final boolean hasPassed;
    private final FilteringErrorType code;
    private final Timestamp timestampBannedUntil;
    private final String message;

    public Json toJsonModel() {
        return new Json(hasPassed,
                code.memorySignature(),
                timestampBannedUntil.letterSignatureFrontendDisplay(),
                message);
    }

    public record Json(
            boolean hasPassed,
            String code,
            String timestampBannedUntil,
            String message
    ) {
    }
}
