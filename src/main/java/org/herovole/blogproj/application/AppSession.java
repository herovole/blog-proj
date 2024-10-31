package org.herovole.blogproj.application;

import java.util.Collection;

public interface AppSession extends AutoCloseable {
    void commit();

    void rollback();

    void insert(Object entity);

    void update(Object entity);

    void upsert(Object entity);

    void delete(Object entity);

    void executeSql(String sql);

    void flushAndClear();

    void flushAndClearByCacheSize(Collection collection);
}
