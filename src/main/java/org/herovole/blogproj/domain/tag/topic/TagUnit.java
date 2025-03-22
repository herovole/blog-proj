package org.herovole.blogproj.domain.tag.topic;

import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IntegerId;

public interface TagUnit extends Comparable<TagUnit> {

    static TagUnit fromFormContent(FormContent formContent) {
        if (formContent == null) return empty();
        return RealTagUnit.builder()
                .id(IntegerId.fromFormContentTopicTagId(formContent))
                .tagJapanese(TagJapanese.fromFormContent(formContent))
                .tagEnglish(TagEnglish.fromFormContent(formContent))
                .build();
    }

    static TagUnit empty() {
        return new EmptyTagUnit();
    }

    IntegerId getId();

    boolean isEmpty();

    boolean hasSameContentWith(TagUnit that);

    Json toJson();

    @Override
    default int compareTo(TagUnit o) {
        return this.getId().compareTo(o.getId());
    }

    interface Json {
    }

}
