package be.algielen;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;

@WebService
public class HelloService {
	private final EntityManager entityManager = SessionManager.INSTANCE.createEntityManager();


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
			entityManager.getTransaction().begin();
			UsersDao usersDao = new UsersDaoImpl();
			if (usernameIsFree(name, usersDao)) {
				User user = usersDao.createUser(name);
				result = true;
			} else {
				result = false;
			}
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
		return result;
	}


	@WebMethod
	@WebResult(name = "user")
	public List<User> presentEveryone() {
		List<User> users = Collections.emptyList();
		try {
			entityManager.getTransaction().begin();
			UsersDao usersDao = new UsersDaoImpl();
			users = usersDao.findAll();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
		return users;
	}

	private boolean usernameIsFree(String name, UsersDao dao) {
		return !dao.exists(name);
	}
}

