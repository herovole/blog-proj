package org.herovole.blogproj.presentation.filter;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.application.FilteringResult;

@RequiredArgsConstructor
public class FilteredErrorResponseBody {

    public static FilteredErrorResponseBody passed() {
        return new FilteredErrorResponseBody(FilteringResult.passed());
    }

    public static FilteredErrorResponseBody internalServerError() {
        return new FilteredErrorResponseBody(FilteringResult.internalServerError());
    }

    public static FilteredErrorResponseBody of(FilteringResult filteringResult) {
        return new FilteredErrorResponseBody(filteringResult);
    }

    private final FilteringResult filteringResult;

    public Json toJsonModel() {
        return new Json(filteringResult.toJsonModel());
    }

    public record Json(FilteringResult.Json filteringResult) {
        public String toJsonString() {
            return new Gson().toJson(this);
        }
    }
}
