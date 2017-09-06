package be.algielen.soap;

import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.algielen.domain.User;
import be.algielen.services.FileArchiverBean;
import be.algielen.services.HelloBean;

@WebService
@MTOM(threshold = 1024)
public class HelloService {
	private final static Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

	@Inject
	private HelloBean helloBean;

	@Inject
	private FileArchiverBean fileArchiverBean = null;

	@PostConstruct
	private void init() {
	}

	@WebMethod
	@WebResult(name = "response")
	public String sayHello(@WebParam(name = "name") String name) {
		LOGGER.info("Hello !");
		return "Hello " + name;
	}

	@WebMethod
	@WebResult(name = "success")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean addUser(@WebParam(name = "name") String name) {
		return helloBean.addUser(name);
	}


	@WebMethod
	@WebResult(name = "user")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<User> presentEveryone() {
		return helloBean.presentEveryone();
	}

	@WebMethod
	@WebResult(name = "success")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean archive(@WebParam(name = "data") byte[] data, @WebParam(name = "filename") String filename, @WebParam(name = "type") String type, @WebParam(name = "sub_directory") String subDirectoryName) {
		boolean result;
		try {
			String fullname = sanitizeForFilesystem(filename) + "." + sanitizeForFilesystem(type);
			Future<FileArchiverBean.State> future = fileArchiverBean.archive(data, fullname, sanitizeForFilesystem(subDirectoryName));
			FileArchiverBean.State state = future.get();
			if (state == FileArchiverBean.State.DONE) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			LOGGER.error("Could not retrieve state of the archiving", e);
			result = false;
		}
		return result;
	}

	private String sanitizeForFilesystem(@WebParam(name = "filename") String filename) {
		return filename.replaceAll("\\W+", "");
	}
}

