package be.algielen.messaging;

import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchivingTask implements Callable<Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchivingTask.class);

    private long whiteboardId;

    public ArchivingTask() {
    }

    @Override
    public Boolean call() throws Exception {
        LOGGER.info("Task started on whiteboard " + whiteboardId);
        return true;
    }

    public long getWhiteboardId() {
        return whiteboardId;
    }

    public void setWhiteboardId(long whiteboardId) {
        this.whiteboardId = whiteboardId;
    }
}
