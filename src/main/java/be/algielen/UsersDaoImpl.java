package be.algielen;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class UsersDaoImpl implements UsersDao {
	private static final Class<User> persistentClass = User.class;
	private final SessionFactory sessionFactory = SessionManager.INSTANCE.getSessionFactory();
	private final Session session;

	public UsersDaoImpl(Session session) {
		this.session = session;
	}

	public User getUser(long id) {
		return getCurrentSession().get(persistentClass, id);
	}

	public User createUser(String name) {
		User user = new User(name);
		getCurrentSession().persist(user);
		return user;
	}

	public boolean exists(String name) {
		Criteria criteria = getCurrentSession().createCriteria(persistentClass);
		criteria.add(Restrictions.eq("name", name));
		return criteria.uniqueResult() != null;
	}

	public List<User> findAll() {
		Criteria criteria = getCurrentSession().createCriteria(persistentClass);
		return criteria.list();
	}

	private Session getCurrentSession() {
		return session;
	}

}
