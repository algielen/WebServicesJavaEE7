package be.algielen;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebParam;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HelloBean {
	@EJB
	private UsersDao dao;
	@Resource
	private SessionContext context;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean addUser(@WebParam(name = "name") String name) {
		boolean result = false;
		try {
			if (usernameIsFree(name)) {
				User user = dao.createUser(name);
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			context.setRollbackOnly();
		}

		return result;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<User> presentEveryone() {
		List<User> users = Collections.emptyList();
		try {
			users = dao.findAll();
		} catch (Exception e) {
			context.setRollbackOnly();
		}
		return users;
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	private boolean usernameIsFree(String name) {
		boolean result = false;
		try {
			result = !dao.exists(name);
		} catch (Exception e) {
			context.setRollbackOnly();
		}
		return result;
	}
}
