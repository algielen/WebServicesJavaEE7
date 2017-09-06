package be.algielen.services;

import java.util.List;

import be.algielen.domain.User;

public interface UsersDao {
	User getUser(long id);

	User getUser(String name);

	User createUser(String name);

	boolean exists(String name);

	List<User> findAll();
}
