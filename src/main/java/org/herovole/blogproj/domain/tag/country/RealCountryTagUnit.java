package org.herovole.blogproj.domain.tag.country;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.tag.topic.TagEnglish;
import org.herovole.blogproj.domain.tag.topic.TagJapanese;

@Builder
@EqualsAndHashCode
public class RealCountryTagUnit implements CountryTagUnit {

    @EqualsAndHashCode.Include
    private final CountryCode id;
    @EqualsAndHashCode.Exclude
    private final TagEnglish tagEnglish;
    @EqualsAndHashCode.Exclude
    private final TagJapanese tagJapanese;


    @Override
    public CountryCode getCountryCode() {
        return this.id;
    }

    @Override
    public CountryTagUnit.Json toJson() {
        return Json.builder()
                .id(this.id.memorySignature())
                .tagEnglish(this.tagEnglish.memorySignature())
                .tagJapanese(this.tagJapanese.memorySignature())
                .build();
    }

    @Builder
    private static class Json implements CountryTagUnit.Json {
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



}
