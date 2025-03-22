package org.herovole.blogproj.domain.helper;


import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class PaginatedResultSet<I, O> {
    /*
    private final long numberOfRecords;
    private ConcurrentLinkedQueue<I> currentQueueOfRecords;
    private PagingRequest currentPaging;

    public PaginatedResultSet(long numberOfRecords, int recordsPerPage)  {
        this.numberOfRecords = numberOfRecords;
        this.currentPaging = new PagingRequest(0, recordsPerPage);
        this.currentQueueOfRecords = new ConcurrentLinkedQueue<>();
    }

    protected abstract O convert(I datasourceRecord);

    public synchronized O next()  {
        if (this.currentQueueOfRecords.isEmpty()) this.reload();
        return this.convert(currentQueueOfRecords.poll());
    }

    protected abstract List<I> reloadPage(PagingRequest paging);

    private synchronized void reload()  {
        if (this.isEmpty()) return;
        this.currentPaging = currentPaging.nextPage();
        this.currentQueueOfRecords = new ConcurrentLinkedQueue<>(this.reloadPage(currentPaging));
    }

    public synchronized boolean hasNext() {
        return !this.isEmpty();
    }

    private synchronized boolean isEmpty() {
        return !this.currentPaging.isInPreparation()
                && this.currentQueueOfRecords.isEmpty()
                && this.numberOfRecords < this.currentPaging.getLastIndexZeroOrigin() + 2;

    }
*/
}
