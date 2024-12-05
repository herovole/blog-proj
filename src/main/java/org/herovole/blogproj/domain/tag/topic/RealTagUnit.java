package org.herovole.blogproj.domain.tag.topic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.IntegerId;

@Builder
@EqualsAndHashCode
public class RealTagUnit implements TagUnit {

    public static RealTagUnit empty() {
        return RealTagUnit.builder().build();
    }

    @Override
    public IntegerId getId() {
        return this.id;
    }

    @EqualsAndHashCode.Include
    private final IntegerId id;
    @EqualsAndHashCode.Exclude
    private final TagEnglish tagEnglish;
    @EqualsAndHashCode.Exclude
    private final TagJapanese tagJapanese;


    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public Json toJson() {
        return RealTagUnit.Json.builder()
                .id(this.id.longMemorySignature())
                .tagEnglish(this.tagEnglish.memorySignature())
                .tagJapanese(this.tagJapanese.memorySignature())
                .build();
    }

    @Builder
    record Json(
            Long id,
            String tagEnglish,
            String tagJapanese
    ) implements TagUnit.Json {
    }

}
