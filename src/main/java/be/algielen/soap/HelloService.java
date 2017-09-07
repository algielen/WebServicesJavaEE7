package be.algielen.soap;

import be.algielen.domain.Document;
import be.algielen.domain.User;
import be.algielen.messaging.DocumentWhiteboard;
import be.algielen.services.FileArchiverBean;
import be.algielen.services.UsersBean;
import be.algielen.utils.FileUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.xml.ws.soap.MTOM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService
@MTOM(threshold = 1024)
public class HelloService {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

    @Inject
    private UsersBean usersBean;

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
    @Transactional(TxType.REQUIRES_NEW)
    public boolean addUser(@WebParam(name = "name") String name) {
        return usersBean.addUser(name);
    }


    @WebMethod
    @WebResult(name = "user")
    @Transactional(TxType.REQUIRES_NEW)
    public List<User> getUsers() {
        return usersBean.getUsers();
    }

    @WebMethod
    @WebResult(name = "success")
    @Transactional(TxType.REQUIRES_NEW)
    public boolean archive(@WebParam(name = "data") byte[] data,
        @WebParam(name = "filename") String filename, @WebParam(name = "type") String type,
        @WebParam(name = "sub_directory") String subDirectoryName) {
        boolean result;
        try {
            String fullname = FileUtils.sanitizeForFilesystem(filename) + "." + FileUtils
                .sanitizeForFilesystem(type);
            result = fileArchiverBean
                .archive(data, fullname, FileUtils.sanitizeForFilesystem(subDirectoryName));
        } catch (Exception e) {
            LOGGER.error("Could not place the file for archiving", e);
            result = false;
        }
        return result;
    }

    @WebMethod
    @WebResult(name = "whiteboard_filenames")
    @Transactional(TxType.REQUIRES_NEW)
    public List<String> getWhiteboard() {
        List<String> filenames = fileArchiverBean.getWhiteboards().stream()
            .map(DocumentWhiteboard::getObject)
            .map(Document::getFilename)
            .collect(Collectors.toList());
        return filenames;
    }
}

