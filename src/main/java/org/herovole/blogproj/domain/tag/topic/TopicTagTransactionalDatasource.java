package org.herovole.blogproj.domain.tag.topic;

import org.herovole.blogproj.application.AppSession;

public interface TopicTagTransactionalDatasource {

    int amountOfCachedTransactions();

    void flush(AppSession session);

    void insert(TagUnit tagUnit);

    void update(TagUnit before, TagUnit after);
}
