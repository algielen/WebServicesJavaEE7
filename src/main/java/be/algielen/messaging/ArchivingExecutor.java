package be.algielen.messaging;

import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ArchivingExecutor {

    private static final int NB_THREADS = 8;
    private static final Logger LOGGER = LoggerFactory.getLogger(ArchivingExecutor.class);

    @Resource
    private ManagedExecutorService executorService;

    @Inject
    private Instance<ArchivingTask> managedTask;

    public ArchivingExecutor() {
    }

    public Future<Boolean> accept(long whiteboardId) {
        LOGGER.info("Received new task : " + whiteboardId); // DEBUG
        ArchivingTask archivingTask = managedTask.get();
        archivingTask.setWhiteboardId(whiteboardId);
        Future<Boolean> future = executorService.submit(archivingTask);
        return future;
    }
}
