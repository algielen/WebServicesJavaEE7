package be.algielen.services;

import be.algielen.domain.User;
import java.util.List;

public interface UsersBean {

    boolean addUser(String name);

    User getUser(long id);

    User getUser(String name);

    List<User> getUsers();
}
