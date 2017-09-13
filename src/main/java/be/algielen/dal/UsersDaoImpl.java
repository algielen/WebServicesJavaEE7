package be.algielen.dal;

import be.algielen.domain.User;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@RequestScoped
@Transactional(value = Transactional.TxType.REQUIRED)
public class UsersDaoImpl implements UsersDao, Serializable {

	private static final Class<User> persistentClass = User.class;
	@PersistenceContext(unitName = "HelloPersistence")
	private EntityManager entityManager;

	public UsersDaoImpl() {
	}


	public User getUser(long id) {
		return entityManager.find(persistentClass, id);
	}

	public User getUser(String name) {
		TypedQuery<User> query = entityManager
				.createQuery("FROM User u WHERE u.name = ?1 ORDER BY u.id ASC", persistentClass);
		query.setParameter(1, name);
		List<User> list = query.getResultList();
		if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
	}


	public User createUser(String name) {
		User user = new User(name);
		entityManager.persist(user);
		return user;
	}

	public boolean exists(String name) {
		TypedQuery<User> query = entityManager
				.createQuery("FROM User u WHERE u.name = ?1", persistentClass);
		query.setParameter(1, name);
		List<User> list = query.getResultList();
		return list.size() > 0;
	}

	public List<User> findAll() {
		return entityManager.createQuery("FROM User u ORDER BY u.name ASC", persistentClass)
				.getResultList();
	}
}
