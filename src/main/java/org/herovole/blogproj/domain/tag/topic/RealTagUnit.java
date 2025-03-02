package org.herovole.blogproj.domain.tag.topic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.IntegerId;

@ToString
@Builder
@EqualsAndHashCode
public class RealTagUnit implements TagUnit {

    @Override
    public IntegerId getId() {
        return this.id;
    }

    @EqualsAndHashCode.Include
    private final IntegerId id;
    @Getter
    @EqualsAndHashCode.Exclude
    private final TagEnglish tagEnglish;
    @Getter
    @EqualsAndHashCode.Exclude
    private final TagJapanese tagJapanese;


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean hasSameContentWith(TagUnit that) {
        if (that.isEmpty()) return false;
        RealTagUnit another = (RealTagUnit) that;
        return this.id.equals(another.id)
                && this.tagEnglish.equals(another.tagEnglish)
                && this.tagJapanese.equals(another.tagJapanese);
    }


    @Override
    public Json toJson() {
        return RealTagUnit.Json.builder()
                .id(this.id.letterSignature())
                .tagEnglish(this.tagEnglish.memorySignature())
                .tagJapanese(this.tagJapanese.memorySignature())
                .build();
    }

    @Builder
    record Json(
            String id,
            String tagEnglish,
            String tagJapanese
    ) implements TagUnit.Json {
    }

}
