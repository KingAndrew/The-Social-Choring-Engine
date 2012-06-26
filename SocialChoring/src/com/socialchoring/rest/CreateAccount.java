package com.socialchoring.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.socialchoring.service.SocialChoringService;
import com.socialchoring.service.SocialChoringServiceImpl;

@Path("/createAccount")
public class CreateAccount {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public long createAccount(
			@QueryParam("userName") String userName,
			@QueryParam("parent_first_name") String parent_first_name,
			@QueryParam("parent_last_name") String parent_last_name,
			@QueryParam("parent_email") String parent_email,
			@QueryParam("player_first_name") String player_first_name) {

		SocialChoringService service = new SocialChoringServiceImpl();
		long id = service.createAccount(userName, parent_first_name, parent_last_name,
				parent_email, player_first_name);

		if (id <= 0)
			throw new RuntimeException("Get: Create Account with Failed.");
		return id;

	}
}
