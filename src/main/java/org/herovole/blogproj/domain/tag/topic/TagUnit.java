package org.herovole.blogproj.domain.tag.topic;

import org.herovole.blogproj.domain.IntegerId;

public interface TagUnit extends Comparable<TagUnit> {

    static TagUnit empty() {
        return new EmptyTagUnit();
    }

    IntegerId getId();

    boolean isEmpty();

    Json toJson();

    @Override
    default int compareTo(TagUnit o) {
        return this.getId().compareTo(o.getId());
    }

    interface Json {
    }

}
