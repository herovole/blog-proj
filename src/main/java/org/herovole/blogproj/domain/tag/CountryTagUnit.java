package org.herovole.blogproj.domain.tag;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.CommentUnit;

@Builder
@EqualsAndHashCode
public class CountryTagUnit implements Comparable<CountryTagUnit> {

    @EqualsAndHashCode.Include
    private final CountryCode id;
    @EqualsAndHashCode.Exclude
    private final TagEnglish tagEnglish;
    @EqualsAndHashCode.Exclude
    private final TagJapanese tagJapanese;

    @Builder
    private static class TagUnitJson {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("tagEnglish")
        @Expose
        private String tagEnglish;
        @SerializedName("tagJapanese")
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

    @Override
    public int compareTo(CountryTagUnit o) {
        return this.id.compareTo(o.id);
    }

}
