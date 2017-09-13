package be.algielen.services;

import be.algielen.dal.UsersDao;
import be.algielen.domain.User;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
@Transactional(value = Transactional.TxType.REQUIRES_NEW)
public class UsersBeanImpl implements UsersBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(UsersBeanImpl.class);
    @Inject
	private UsersDao dao;

    public UsersBeanImpl() {
    }

	@Override
	public boolean addUser(@WebParam(name = "name") String name) {
		boolean result = false;
		if (usernameIsFree(name)) {
			User user = dao.createUser(name);
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public User getUser(long id) {
		return dao.getUser(id);
	}

	@Override
	public User getUser(String name) {
		return dao.getUser(name);
	}

	@Override
    public List<User> getUsers() {
        return dao.findAll();
	}

	@Transactional(value = Transactional.TxType.REQUIRED)
	boolean usernameIsFree(String name) {
		return !dao.exists(name);
	}
}
