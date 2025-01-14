package org.herovole.blogproj.presentation.filter;

import com.google.gson.Gson;
import lombok.Builder;

@Builder
record BlockedByFilterResponseBody(String code, String timestampBannedUntil, String message) {
    static final String FILTER_CODE_SERVER_ERROR = "SVR";
    String toJsonString() {
        return new Gson().toJson(this);
    }
}
