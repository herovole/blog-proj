package org.herovole.blogproj.domain.tag.topic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.time.Timestamp;

@Builder
@EqualsAndHashCode
public class RealTagUnitWithStat implements TagUnit {

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
    @EqualsAndHashCode.Exclude
    private final int articles;
    @EqualsAndHashCode.Exclude
    private final Timestamp lastUpdate;


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean hasSameContentWith(TagUnit that) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Json toJson() {
        return RealTagUnitWithStat.Json.builder()
                .id(this.id.letterSignature())
                .tagEnglish(this.tagEnglish.memorySignature())
                .tagJapanese(this.tagJapanese.memorySignature())
                .articles(this.articles)
                .lastUpdate(this.lastUpdate.letterSignatureYyyyMMddSpaceHHmmss())
                .build();
    }

    @Builder
    record Json(
            String id,
            String tagEnglish,
            String tagJapanese,
            int articles,
            String lastUpdate
    ) implements TagUnit.Json {
    }

}
