package be.algielen.messaging;

import be.algielen.dal.DocumentWhiteboardDao;
import be.algielen.domain.Document;
import be.algielen.services.ArchivingFolderBean;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@MessageDriven(mappedName = "java:/jms/queue/archiving",
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination",
            propertyValue = "java:/jms/queue/archiving")
    })
public class ArchivingReceiver implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchivingReceiver.class);

    @Resource
    private MessageDrivenContext mdc;

    @Inject
    private DocumentWhiteboardDao documentWhiteboardDao;

    @Inject
    private ArchivingFolderBean archivingFolderBean;

    public ArchivingReceiver() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            Long whiteboardId = message.getBody(Long.class);
            LOGGER.debug("Received async message for " + whiteboardId);
            if (whiteboardId != null) {
                writeDocument(whiteboardId);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to process message", e);
        }
    }

    @Transactional(TxType.REQUIRES_NEW)
    public void writeDocument(long whiteboardId) throws IOException {
        Date now = new Date();
        String time = String.valueOf(now.getTime());

        DocumentWhiteboard whiteboard = documentWhiteboardDao.load(whiteboardId);
        Document document = whiteboard.getObject();
        String filename = document.getFilename();

        File rootDirectory = archivingFolderBean.getRootDirectory();
        Path filepath = new File(rootDirectory, filename).toPath();
        Path newPath = new File(rootDirectory, time + "." + document.getExtension()).toPath();
        Files.write(newPath, document.getContent());

        LOGGER.debug("File written at " + newPath);
        whiteboard.setState(State.DONE);
        whiteboard.setTreatmentTime(now);
    }
}
