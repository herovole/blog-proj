package org.herovole.blogproj.domain.tag;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import org.herovole.blogproj.domain.IntegerId;

@Builder
public class TagUnit {

    private final IntegerId id;
    private final TagEnglish tagEnglish;
    private final TagJapanese tagJapanese;

    @Builder
    private static class TagUnitJson {
        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("tagEnglish")
        @Expose
        private String tagEnglish;
        @SerializedName("tagEnglish")
        @Expose
        private String tagJapanese;
    }

    public String toJsonString() {
        TagUnitJson model = TagUnitJson.builder()
                .id(this.id.memorySignature())
                .tagEnglish(this.tagEnglish.memorySignature())
                .tagJapanese(this.tagJapanese.memorySignature())
                .build();
        return new Gson().toJson(model);
    }

}
