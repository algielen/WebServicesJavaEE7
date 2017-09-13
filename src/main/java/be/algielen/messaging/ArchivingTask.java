package be.algielen.messaging;

import be.algielen.dal.DocumentWhiteboardDao;
import be.algielen.domain.Document;
import be.algielen.services.ArchivingFolderBean;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchivingTask implements Callable<Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchivingTask.class);

    private long whiteboardId;

    @Inject
    private DocumentWhiteboardDao documentWhiteboardDao;

    @Inject
    private ArchivingFolderBean archivingFolderBean;

    // DEBUG
    @PersistenceContext(unitName = "HelloPersistence")
    private EntityManager entityManager;


    public ArchivingTask() {
    }

    @Override
    public Boolean call() throws Exception {
        LOGGER.info("Task started on whiteboard " + whiteboardId);
        boolean result = writeDocument(getWhiteboardId());

        return result;
    }

    @Transactional(TxType.REQUIRES_NEW)
    public boolean writeDocument(long whiteboardId) throws IOException {
        LOGGER.info("writeDocument");
        DocumentWhiteboard whiteboard = entityManager
            .find(DocumentWhiteboard.class, whiteboardId);
        //DocumentWhiteboard whiteboard = documentWhiteboardDao.load(whiteboardId);
        Document document = whiteboard.getObject();
        String filename = document.getFilename();

        File rootDirectory = archivingFolderBean.getRootDirectory();
        Path filepath = new File(rootDirectory, filename).toPath();
        Files.write(filepath, document.getContent());
        LOGGER.info("File written in " + filepath);
        whiteboard.setState(State.DONE);

        return true;

    }

    public long getWhiteboardId() {
        return whiteboardId;
    }

    public void setWhiteboardId(long whiteboardId) {
        this.whiteboardId = whiteboardId;
    }
}
