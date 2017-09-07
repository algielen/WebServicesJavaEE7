package be.algielen.services;

import be.algielen.utils.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class FileArchiverBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileArchiverBean.class);

    public enum State {WAITING, STARTING, PROCESSING, FAILURE, DONE}

    @Inject
    private ArchivingFolderBean archivingFolderBean;

    @Asynchronous
    public Future<State> archive(byte[] data, String filename, String subDirectoryName) {
        State state;
        try {
            File rootDirectory = archivingFolderBean.getRootDirectory();
            File subDirectory = new File(rootDirectory, subDirectoryName);
            FileUtils.ensureFolderExists(rootDirectory, subDirectory);
            File file = new File(subDirectory, filename);
            LOGGER.info("Writing new archive at : " + file.getPath());
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.close();
            state = State.DONE;
        } catch (IOException e) {
            state = State.FAILURE;
            LOGGER.error("Could not write file", e);
        }
        return new AsyncResult<>(state);
    }
}
