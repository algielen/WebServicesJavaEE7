package be.algielen;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class HelloService {
	private List<User> users;

	@PostConstruct
	private void init() {
		users = new ArrayList<>();
	}

	@WebMethod
	public String sayHello(@WebParam(name = "name") String name) {
		return "Hello " + name;
	}

	@WebMethod
	public boolean addUser(@WebParam(name = "name") String name) {
		if (usernameIsFree(name)) {
			User user = UsersFactory.create(name);
			users.add(user);
			return true;
		} else {
			return false;
		}
	}

	@WebMethod
	public List<User> presentEveryone() {
		return users;
	}

	private boolean usernameIsFree(String name) {
		return users.stream().noneMatch(user -> user.getName().equals(name));
	}
}
