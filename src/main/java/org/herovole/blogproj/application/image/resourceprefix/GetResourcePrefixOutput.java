package org.herovole.blogproj.application.image.resourceprefix;

import com.google.gson.Gson;
import lombok.Builder;

@Builder
public class GetResourcePrefixOutput {
    private final String prefix;

    public Json toJsonModel() {
        return new Json(prefix);
    }

    public record Json(String prefix) {
        public String toJsonString() {
            return new Gson().toJson(this);
        }
    }
}
