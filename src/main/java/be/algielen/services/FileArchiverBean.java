package be.algielen.services;

import be.algielen.dal.DocumentDao;
import be.algielen.dal.DocumentWhiteboardDao;
import be.algielen.domain.Document;
import be.algielen.messaging.ArchivingSender;
import be.algielen.messaging.DocumentWhiteboard;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class FileArchiverBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileArchiverBean.class);

    @Inject
    private ArchivingFolderBean archivingFolderBean;

    @Inject
    private ArchivingSender archivingSender;

    @Inject
    private DocumentDao documentDao;

    @Inject
    private DocumentWhiteboardDao documentWhiteboardDao;


    @Transactional(TxType.REQUIRED)
    public boolean archive(byte[] data, String filename, String subDirectoryName) {
        boolean result;
        try {
            Date now = new Date();

            Document document = new Document();
            document.setFilename(filename);
            document.setContent(data);
            documentDao.persist(document);

            DocumentWhiteboard whiteboard = new DocumentWhiteboard();
            whiteboard.setInsertionTime(now);
            whiteboard.setObject(document);
            whiteboard.setState(DocumentWhiteboard.State.WAITING);
            documentWhiteboardDao.persist(whiteboard);

            archivingSender.sendMessage(whiteboard.getId());

            result = true;
        } catch (Exception e) {
            LOGGER.error("Could not create whiteboard", e);
            result = false;
        }
        return result;
    }

    @Transactional(TxType.REQUIRED)
    public List<DocumentWhiteboard> getWhiteboards() {
        List<DocumentWhiteboard> list = Collections.emptyList();
        try {
            list = documentWhiteboardDao.findAll();
        } catch (Exception e) {
            LOGGER.error("Couldn't get all document whiteboards", e);
        }
        return list;
    }
}
