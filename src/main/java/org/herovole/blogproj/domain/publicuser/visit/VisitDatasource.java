package org.herovole.blogproj.domain.publicuser.visit;

import org.herovole.blogproj.application.AppSession;

public interface VisitDatasource {
    int amountOfCachedTransactions();

    void flush(AppSession session);

    void report(Visit visit);
}
