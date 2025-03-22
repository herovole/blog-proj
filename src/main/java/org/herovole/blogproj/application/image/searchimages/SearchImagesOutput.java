package org.herovole.blogproj.application.image.searchimages;

import com.google.gson.Gson;
import lombok.Builder;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.Images;

@Builder
public class SearchImagesOutput {
    private final Images images;
    private final int total;

    public Json toJsonModel() {
        return new Json(images.toJsonModel(), total);
    }

    public record Json(Image.Json[] files, int total) {
        public String toJsonString() {
            return new Gson().toJson(this);
        }
    }
}
