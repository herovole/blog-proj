package org.herovole.blogproj.domain.tag.topic;

import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;

public interface TopicTagDatasource {
    TagUnit findById(IntegerId tagId);

    TagUnits search(PagingRequest pagingRequest);

    long countAll();
}
