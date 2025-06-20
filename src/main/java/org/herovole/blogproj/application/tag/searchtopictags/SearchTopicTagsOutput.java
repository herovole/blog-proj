package org.herovole.blogproj.application.tag.searchtopictags;

import lombok.Builder;
import org.herovole.blogproj.domain.tag.topic.TagUnit;
import org.herovole.blogproj.domain.tag.topic.TagUnits;

@Builder
public class SearchTopicTagsOutput {
    private final TagUnits tagUnits;
    private final long total;

    public Json toJsonModel() {
        return new Json(tagUnits.toJson(), total);
    }

    public record Json(TagUnit.Json[] tagUnits,
                long total) {
    }
}
