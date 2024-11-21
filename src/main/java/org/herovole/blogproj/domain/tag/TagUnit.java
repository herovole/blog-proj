package org.herovole.blogproj.domain.tag;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.IntegerId;

@Builder
@EqualsAndHashCode
public class TagUnit implements Comparable<TagUnit> {

    @EqualsAndHashCode.Include
    private final IntegerId id;
    @EqualsAndHashCode.Exclude
    private final TagEnglish tagEnglish;
    @EqualsAndHashCode.Exclude
    private final TagJapanese tagJapanese;

    @Builder
    private static class TagUnitJson {
        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("tagEnglish")
        @Expose
        private String tagEnglish;
        @SerializedName("tagJapanese")
        @Expose
        private String tagJapanese;
    }

    public String toJsonString() {
        TagUnitJson model = TagUnitJson.builder()
                .id(this.id.longMemorySignature())
                .tagEnglish(this.tagEnglish.memorySignature())
                .tagJapanese(this.tagJapanese.memorySignature())
                .build();
        return new Gson().toJson(model);
    }

    @Override
    public int compareTo(TagUnit o) {
        return this.id.compareTo(o.id);
    }

}
