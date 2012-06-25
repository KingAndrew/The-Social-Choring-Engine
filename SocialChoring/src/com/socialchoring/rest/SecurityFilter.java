package com.socialchoring.rest;

import javax.ws.rs.WebApplicationException;

import com.socialchoring.bean.User;
import com.socialchoring.service.SocialChoringService;
import com.socialchoring.service.SocialChoringServiceImpl;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class SecurityFilter implements ContainerRequestFilter {

	/**
	 * Authenticate the user for this request, and add a security context so
	 * that role checking can be performed.
	 * 
	 * @param request
	 *            The request we re processing
	 * @return the decorated request
	 */
	public ContainerRequest filter(ContainerRequest request) {
		User user = authenticate(request);
		request.setSecurityContext(new Authorizer(user));
		return request;
	}

	/**
	 * Perform the required authentication checks, and return the User instance
	 * for the authenticated user.
	 * 
	 * @param request
	 */
	private User authenticate(ContainerRequest request) {
		String authentication = request.getHeaderValue(ContainerRequest.AUTHORIZATION);
		if (authentication == null) {
			System.err.println("no authentication info found");
			return null;
		}
		if (!authentication.startsWith("Basic ")) {
			return null;
			// additional checks should be done here
			// "Only HTTP Basic authentication is supported"
		}
		authentication = authentication.substring("Basic ".length());
		String[] values = new String(Base64.base64Decode(authentication)).split(":");
		if (values.length < 2) {
			throw new WebApplicationException(400);
			// "Invalid syntax for username and password"
		}
		String usernameOrEmail = values[0];
		String password = values[1];
		if ((usernameOrEmail == null) || (password == null)) {
			throw new WebApplicationException(400);
			// "Missing username or password"
		}

		SocialChoringService service = new SocialChoringServiceImpl();
		User user = service.login(usernameOrEmail, usernameOrEmail, password);
		if (user != null) {
			System.out.println("USER AUTHENTICATED");
			// } else if (username.equals("admin") &&
			// password.equals("adminadmin")) {
			// user = new User("admin", "admin");
			// System.out.println("ADMIN AUTHENTICATED");
		} else {
			System.out.println("USER NOT AUTHENTICATED");
			throw new WebApplicationException(400);
		}
		return user;
	}
}