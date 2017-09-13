package be.algielen.dal;

import be.algielen.domain.User;
import java.util.List;

public interface UsersDao {

    User getUser(long id);

    User getUser(String name);

    User createUser(String name);

    boolean exists(String name);

    List<User> findAll();
}
