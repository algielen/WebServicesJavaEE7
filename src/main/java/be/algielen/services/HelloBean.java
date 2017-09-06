package be.algielen.services;

import java.util.List;

import be.algielen.domain.User;

public interface HelloBean {
	boolean addUser(String name);

	User getUser(long id);

	User getUser(String name);

	List<User> presentEveryone();
}
