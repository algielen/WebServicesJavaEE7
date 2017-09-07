package be.algielen.rest;

import be.algielen.domain.User;
import be.algielen.services.UsersBean;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

// TODO proper responses
@Path("users")
public class UsersRest {

	@Context
	private UriInfo context;

	@Inject
    private UsersBean usersBean;

	public UsersRest() {
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{name}")
	public Response getUser(@PathParam("name") String name) {
        return Response.ok(usersBean.getUser(name)).build();
    }

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{name}")
	public Response addUser(@PathParam("name") String name) {
        boolean success = usersBean.addUser(name);
        if (success) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.CONFLICT).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getUsers() {
        List<User> users = usersBean.getUsers();
        GenericEntity<List<User>> list = new GenericEntity<List<User>>(users) {
		};
		return Response.ok(list).build();
	}
}
