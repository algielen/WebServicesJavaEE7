package be.algielen.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class FileArchiverBean {
	private final static Logger LOGGER = LoggerFactory.getLogger(FileArchiverBean.class);

	private File rootDirectory;

	public enum State {WAITING, STARTING, PROCESSING, FAILURE, DONE}

	@PostConstruct
	@Lock(LockType.WRITE)
	private void init() {
		try {
			InputStream file = this.getClass().getResourceAsStream("/archiving.properties");
			Properties properties = new Properties();
			properties.load(file);
			String rootPath = properties.getProperty("rootPath");
			rootDirectory = new File(rootPath);
			ensureFolderExists(rootDirectory);
		} catch (IOException e) {
			LOGGER.error("Failed to load root archiving directory from properties", e);
			rootDirectory = createBackupFolder();

		}
	}

	@Lock(LockType.WRITE)
	@Asynchronous
	public Future<State> archive(byte[] data, String filename, String subDirectoryName) {
		State state;
		try {
			File subDirectory = new File(rootDirectory, subDirectoryName);
			ensureFolderExists(subDirectory);
			File file = new File(subDirectory, filename);
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

	private void ensureFolderExists(File folder) throws IOException {
		if (!folder.exists()) {
			if (!folder.mkdirs()) {
				throw new IOException("Folder cannot be created at " + rootDirectory.getPath());
			}
		} else if (folder.exists() && !folder.isDirectory()) {
			throw new IOException("Folder already exists but is a file");
		}
	}

	private File createBackupFolder() {
		try {
			File directory = new File("~/archiving-not-found/");
			ensureFolderExists(rootDirectory);
			return directory;
		} catch (Exception e) {
			throw new RuntimeException("Could not create backup folder");
		}
	}

}
