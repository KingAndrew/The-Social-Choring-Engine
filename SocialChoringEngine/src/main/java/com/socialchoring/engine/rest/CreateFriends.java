package com.socialchoring.engine.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;

@Path("/createFriends")
public class CreateFriends {
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createFriends(@FormParam("playerOneId") long playerOneId, @FormParam("playerTwoId") long playerTwoId) {
		SocialChoringService service = new SocialChoringServiceImpl();
		boolean success = service.createFriends(new Date(), playerOneId, playerTwoId);
		return String.valueOf(success);
	}
}
