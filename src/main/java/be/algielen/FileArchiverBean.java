package be.algielen;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO : move java.io out of EJB
@Stateful
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class FileArchiverBean {
	private final static Logger LOGGER = LoggerFactory.getLogger(FileArchiverBean.class);

	private File rootDirectory;

	public enum State {WAITING, STARTING, PROCESSING, FAILURE, DONE}

	@PostConstruct
	private void init() {
		try {
			InputStream file = this.getClass().getResourceAsStream("/archiving.properties");
			Properties properties = new Properties();
			properties.load(file);
			String rootPath = properties.getProperty("rootPath");
			rootDirectory = new File(rootPath);
			if (!rootDirectory.exists()) {
				rootDirectory.mkdir();
			}
		} catch (IOException e) {
			LOGGER.error("Failed to load root archiving directory from properties", e);
			rootDirectory = new File("C:/temp/archiving-not-found/");
		}
	}

	@Lock(LockType.WRITE)
	@Asynchronous
	public Future<State> archive(byte[] data, String filename, String subDirectoryName) {
		State state;
		// TODO : clean subdir and name
		try {
			File subDirectory = new File(rootDirectory, subDirectoryName);
			if (!subDirectory.exists()) {
				subDirectory.mkdir();
			}
			File file = new File(subDirectory, filename);
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
			outputStream.write(data);
			state = State.DONE;
		} catch (IOException e) {
			state = State.FAILURE;
			LOGGER.error("Could not write file", e);
		}
		return new AsyncResult<State>(state);
	}

}
