package com.socialchoring.engine.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;

@Path("/addNewPlayerToAccount")
public class AddNewPlayerToAccount {

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addNewPlayerToAccount(
			@FormParam("accountId") long accountId,
			@FormParam("playerFirstName") String playerFirstName) {
		SocialChoringService service = new SocialChoringServiceImpl();
		boolean success = service.addNewPlayerToAccount(accountId,
				playerFirstName);
		return String.valueOf(success);
	}
}
