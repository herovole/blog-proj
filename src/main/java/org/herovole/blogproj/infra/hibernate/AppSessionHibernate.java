package org.herovole.blogproj.infra.hibernate;

import org.herovole.blogproj.application.AppSession;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.Collection;

public class AppSessionHibernate implements AppSession {

    private static final int HIBERNATE_BATCH_SIZE = 50;

    public static AppSessionHibernate of(Session session) {
        return new AppSessionHibernate(session);
    }

    private final Session session;
    private final Transaction transaction;

    private boolean isSuccessful = false;

    private AppSessionHibernate(Session session) {
        this.session = session;
        this.transaction = session.beginTransaction();
    }

    @Override
    public void commit() {
        transaction.commit();
        isSuccessful = true;
    }

    @Override
    public void rollback() {
        transaction.rollback();
    }

    @Override
    public void insert(Object entity) {
        session.save(entity);
    }

    @Override
    public void update(Object entity) {
        session.update(entity);
    }

    @Override
    public void upsert(Object entity) {
        session.saveOrUpdate(entity);
    }

    @Override
    public void delete(Object entity) {
        session.delete(entity);
    }

    @Override
    public void executeSql(String sql) {
        Query query = session.createQuery(sql);
        query.executeUpdate();
    }

    @Override
    public void flushAndClear() {
        session.flush();
        session.clear();
    }

    @Override
    public void flushAndClearByCacheSize(Collection collection) {
        if (!collection.isEmpty() && collection.size() % AppSessionHibernate.HIBERNATE_BATCH_SIZE == 0) {
            this.flushAndClear();
        }
    }

    @Override
    public void close() {
        if (!isSuccessful) transaction.rollback();
        session.close();
    }
}
