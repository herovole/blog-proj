package org.herovole.blogproj.presentation.presenter;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.presentation.ControllerErrorType;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicResponseBody<T> {

    private final T contentJsonModel;
    private final ControllerErrorType code;
    private final Timestamp timestampBannedUntil;
    private final String message;

    public Json<T> toJsonModel() {
        return new Json<>(
                contentJsonModel,
                code.getAppErrorCode(),
                timestampBannedUntil.letterSignatureFrontendDisplay(),
                message);
    }

    public record Json<T>(
            T content,
            String code,
            String timestampBannedUntil,
            String message
    ) {
        public String toJsonString() {
            return new Gson().toJson(this);
        }
    }
}
