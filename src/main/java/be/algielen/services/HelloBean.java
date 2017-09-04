package be.algielen.services;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import be.algielen.domain.User;

@Local
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface HelloBean {
	boolean addUser(String name);

	User getUser(long id);

	User getUser(String name);

	List<User> presentEveryone();
}
