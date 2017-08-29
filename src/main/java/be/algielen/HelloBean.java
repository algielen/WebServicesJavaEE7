package be.algielen;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebParam;

@Local
public interface HelloBean {
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	boolean addUser(@WebParam(name = "name") String name);

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	List<User> presentEveryone();
}
