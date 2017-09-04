package be.algielen.services;

import java.util.List;

import javax.ejb.Local;

import be.algielen.domain.User;

@Local
public interface UsersDao {
	User getUser(long id);

	User getUser(String name);

	User createUser(String name);

	boolean exists(String name);

	List<User> findAll();
}
