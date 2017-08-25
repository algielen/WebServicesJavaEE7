package be.algielen;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.hibernate.Session;
import org.hibernate.Transaction;

@WebService
public class HelloService {

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

		Transaction tx = null;
		try (Session session = openSession()) {
			tx = session.beginTransaction();
			UsersDao usersDao = new UsersDaoImpl(session);
			if (usernameIsFree(name, usersDao)) {
				User user = usersDao.createUser(name);
				result = true;
			} else {
				result = false;
			}
			tx.commit();
		} catch (Exception e) {
			SessionUtils.rollback(tx);
		}
		return result;
	}


	@WebMethod
	@WebResult(name = "user")
	public List<User> presentEveryone() {
		List<User> users = Collections.emptyList();
		Transaction tx = null;
		try (Session session = openSession()) {
			tx = session.beginTransaction();
			UsersDao usersDao = new UsersDaoImpl(session);
			users = usersDao.findAll();
			tx.commit();
		} catch (Exception e) {
			SessionUtils.rollback(tx);
		}
		return users;
	}

	private boolean usernameIsFree(String name, UsersDao dao) {
		return !dao.exists(name);
	}

	private Session openSession() {
		return SessionManager.INSTANCE.getSessionFactory().openSession();
	}
}

