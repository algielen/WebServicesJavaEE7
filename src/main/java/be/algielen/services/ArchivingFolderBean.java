package be.algielen.services;

import be.algielen.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ArchivingFolderBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(ArchivingFolderBean.class);
    private static final String backupDir = "~/archiving-not-found/";
    private File rootDirectory;

    @PostConstruct
    private void init() {
        try {
            InputStream file = this.getClass().getResourceAsStream("/archiving.properties");
            Properties properties = new Properties();
            properties.load(file);
            String rootPath = properties.getProperty("rootPath");
            rootDirectory = new File(rootPath);
            FileUtils.ensureFolderExists(rootDirectory, rootDirectory);
        } catch (IOException e) {
            LOGGER.error("Failed to load root archiving directory from properties", e);
            rootDirectory = createBackupFolder();

        }
    }

    File getRootDirectory() {
        return rootDirectory;
    }

    private File createBackupFolder() {
        try {
            File directory = new File(backupDir);
            FileUtils.ensureFolderExists(rootDirectory, rootDirectory);
            return directory;
        } catch (Exception e) {
            throw new RuntimeException("Could not create backup folder");
        }
    }
}
