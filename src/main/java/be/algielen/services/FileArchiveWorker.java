package be.algielen.services;

import be.algielen.messaging.ArchivingSender;
import javax.enterprise.context.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class FileArchiveWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchivingSender.class);


    public void accept(long whiteboardId) {
        LOGGER.info("I got a message woohoo !");

        /*File rootDirectory = archivingFolderBean.getRootDirectory();
        File subDirectory = new File(rootDirectory, subDirectoryName);
        FileUtils.ensureFolderExists(rootDirectory, subDirectory);
        File file = new File(subDirectory, filename);
        LOGGER.info("Writing new archive at : " + file.getPath());
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(data);
        outputStream.close();*/
    }

}
