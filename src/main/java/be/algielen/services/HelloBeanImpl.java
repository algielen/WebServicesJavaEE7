package be.algielen.services;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.algielen.domain.User;

@Stateless
@Local(HelloBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HelloBeanImpl implements HelloBean {
	private final static Logger LOGGER = LoggerFactory.getLogger(HelloBeanImpl.class);
	@EJB
	private UsersDao dao;
	@Resource
	private SessionContext context;

	@Override
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
			LOGGER.error("Could not add user", e);
			context.setRollbackOnly();
		}

		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public User getUser(long id) {
		User result = null;
		try {
			result = dao.getUser(id);
		} catch (Exception e) {
			LOGGER.error("Could not get user " + id, e);
			context.setRollbackOnly();
		}
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public User getUser(String name) {
		User result = null;
		try {
			result = dao.getUser(name);
		} catch (Exception e) {
			LOGGER.error("Could not get user " + name, e);
			context.setRollbackOnly();
		}
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<User> presentEveryone() {
		List<User> users = Collections.emptyList();
		try {
			users = dao.findAll();
		} catch (Exception e) {
			LOGGER.error("Could not retrieve users list", e);
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
			LOGGER.error("Could not check if user exists", e);
			context.setRollbackOnly();
		}
		return result;
	}
}
