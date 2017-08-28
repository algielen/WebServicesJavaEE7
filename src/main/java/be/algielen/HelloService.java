package be.algielen;

import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@WebService
public class HelloService {

    @PersistenceContext(unitName = "HelloPersistence")
    private EntityManager entityManager;

    @Resource
    private UserTransaction tx;

    @PostConstruct
    private void init() {
    }

    @WebMethod
    @WebResult(name = "response")
    public String sayHello(@WebParam(name = "name") String name) {
        return "Hello " + name;
    }

    @WebMethod
    @WebResult(name = "success")
    public boolean addUser(@WebParam(name = "name") String name) {
        boolean result = false;
        try {
            tx.begin();
            UsersDao usersDao = new UsersDaoImpl();
            if (usernameIsFree(name, usersDao)) {
                User user = usersDao.createUser(name);
                result = true;
            } else {
                result = false;
            }
            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }


    @WebMethod
    @WebResult(name = "user")
    public List<User> presentEveryone() {
        List<User> users = Collections.emptyList();
        try {
            tx.begin();
            UsersDao usersDao = new UsersDaoImpl();
            users = usersDao.findAll();
            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }
        return users;
    }

    private boolean usernameIsFree(String name, UsersDao dao) {
        return !dao.exists(name);
    }
}

