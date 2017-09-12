package be.algielen.messaging;

import be.algielen.services.FileArchiveWorker;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// created on the server
//@JMSDestinationDefinition(
//    name = "java:/jms/queue/archiving",
//    interfaceName = "javax.jms.Queue",
//    destinationName = "archiving")

@MessageDriven(mappedName = "java:/jms/queue/archiving",
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination",
            propertyValue = "java:/jms/queue/archiving")/*
        @ActivationConfigProperty(propertyName = "subscriptionDurability",
            propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "clientId",
            propertyValue = "archivingId"),
        @ActivationConfigProperty(propertyName = "subscriptionName",
            propertyValue = "archivingSub")*/})
public class ArchivingReceiver implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchivingReceiver.class);

    @Resource
    private MessageDrivenContext mdc;

    @Inject
    private FileArchiveWorker worker;

    public ArchivingReceiver() {
    }

    @PostConstruct
    public void init() {
        LOGGER.info("MDB created");
    }

    @Override
    public void onMessage(Message message) {
        try {
            Long whiteboardId = message.getBody(Long.class);
            LOGGER.info("Received async message for " + whiteboardId);
            if (whiteboardId != null) {
                worker.accept(whiteboardId);
            }
        } catch (JMSException e) {
            LOGGER.error("Failed to process message", e);
        }
    }
}
