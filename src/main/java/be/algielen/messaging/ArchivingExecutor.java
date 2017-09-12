package be.algielen.messaging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ArchivingExecutor {

    private static final int NB_THREADS = 8;
    private static final Logger LOGGER = LoggerFactory.getLogger(ArchivingExecutor.class);
    private ExecutorService executorService = Executors.newFixedThreadPool(NB_THREADS);

    public ArchivingExecutor() {
    }

    public Future<Boolean> accept(long whiteboardId) {
        LOGGER.info("Received new task : " + whiteboardId); // DEBUG
        ArchivingTask task = new ArchivingTask();
        task.setWhiteboardId(whiteboardId);
        Future<Boolean> future = executorService.submit(task);
        return future;
    }
}
