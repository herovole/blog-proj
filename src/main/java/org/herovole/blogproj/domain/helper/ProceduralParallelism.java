package org.herovole.blogproj.domain.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class ProceduralParallelism {
    private static final Logger logger = LoggerFactory.getLogger(ProceduralParallelism.class.getSimpleName());
    private final int threadNum;
    private final ExecutorService executorService;

    protected ProceduralParallelism(int threadNum) {
        this.threadNum = threadNum;
        this.executorService = Executors.newFixedThreadPool(threadNum);
    }

    public boolean run() throws Exception {
        final ProceduralParallelism it = this;
        final List<Future<Boolean>> calcs = new ArrayList<>();
        Boolean result = true;
        try {
            for (int i = 0; i < threadNum; i++) {
                calcs.add(executorService.submit(it::foreachThread));
            }
            for (Future<Boolean> calc : calcs) {
                result = result && calc.get();
            }
        } catch (Exception e) {
            logger.error("Error during multi-threaded operation.", e);
            this.resign();
            throw e;
        } finally {
            executorService.shutdown();
        }
        return result;
    }

    protected abstract boolean foreachThread() throws Exception;

    protected void resign() {
        this.executorService.shutdownNow();
    }
}
