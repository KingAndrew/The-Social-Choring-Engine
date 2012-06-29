package com.socialchoring.engine.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;

@Path("/addNewPlayerToAccount")
public class AddNewPlayerToAccount {

	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public boolean addNewPlayerToAccount(
			@QueryParam("accountId") long accountId,
			@QueryParam("playerFistName") String playerFistName) {
		SocialChoringService service = new SocialChoringServiceImpl();
		boolean success = service.addNewPlayerToAccount(accountId,
				playerFistName);
		return success;
	}
}
