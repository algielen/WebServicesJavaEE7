package be.algielen.messaging;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class ArchivingSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchivingSender.class);

    //@Resource(mappedName = "jms.topic.archivingDestination")
    //private Topic topic;

    @Resource(mappedName = "java:/jms/queue/archiving")
    private Queue queue;


    @Inject
    private JMSContext jmsContext;

    public void sendMessage(Long whiteboardId) {
        try {
            jmsContext.createProducer().send(queue, whiteboardId);
        } catch (Exception e) {
            LOGGER.error("Failed to send message for id ", whiteboardId, e);
        }
    }


}
