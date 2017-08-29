package be.algielen;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public class HelloService {
	@EJB
	private HelloBean helloBean;

	@PostConstruct
	private void init() {
	}

	@WebMethod
	@WebResult(name = "response")
	public String sayHello(@WebParam(name = "name") String name) {
		return "Hello " + name;
	}

	@WebMethod
	@WebResult(name = "success")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean addUser(@WebParam(name = "name") String name) {
		return helloBean.addUser(name);
	}


	@WebMethod
	@WebResult(name = "user")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<User> presentEveryone() {
		return helloBean.presentEveryone();
	}
}

