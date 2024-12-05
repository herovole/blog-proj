package org.herovole.blogproj.domain.tag.topic;

import org.herovole.blogproj.domain.IntegerId;

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
    public Json toJson() {
        return null;
    }


}
