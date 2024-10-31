package org.herovole.blogproj.infra.hibernate;

import org.herovole.blogproj.application.AppSession;

import java.util.Collection;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class TransactionCache<T> {
    protected final Queue<T> cache = new ConcurrentLinkedQueue<>();

    public final synchronized void add(T transaction) {
        if(!this.contains(transaction)) cache.add(transaction);
    }

    public final void add(T[] transactions) {
        Collections.addAll(cache, transactions);
    }

    public final void add(Collection<T> transactions) {
        cache.addAll(transactions);
    }

    public final int amountOfStackedTransactions() {
        return cache.size();
    }

    public final void flush(AppSession session) {
        while(!cache.isEmpty()) {
            this.doTransaction(cache.poll(), session);
            session.flushAndClearByCacheSize(cache);
        }
        session.flushAndClear();
    }

    protected abstract void doTransaction(T transaction, AppSession session);

    protected synchronized boolean contains(T transaction) { return cache.contains(transaction); }
}
