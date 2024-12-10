package org.herovole.blogproj.domain.tag.topic;

import lombok.ToString;
import org.herovole.blogproj.domain.IntegerId;

@ToString
public class EmptyTagUnit implements TagUnit {
    @Override
    public IntegerId getId() {
        return IntegerId.empty();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean hasSameContentWith(TagUnit that) {
        return that.isEmpty();
    }

    @Override
    public Json toJson() {
        return null;
    }


}
