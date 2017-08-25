package be.algielen;

import java.util.List;

public interface UsersDao {
	User getUser(long id);

	User createUser(String name);

	boolean exists(String name);

	List<User> findAll();
}
