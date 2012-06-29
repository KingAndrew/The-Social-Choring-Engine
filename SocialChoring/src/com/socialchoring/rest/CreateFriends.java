package com.socialchoring.rest;

import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.socialchoring.service.SocialChoringService;
import com.socialchoring.service.SocialChoringServiceImpl;

@Path("/createFriends")
public class CreateFriends {
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public boolean createFriends(@QueryParam("playerOneId") long playerOneId,
			@QueryParam("playerTwoId") long playerTwoId) {
		SocialChoringService service = new SocialChoringServiceImpl();
		boolean success = service.createFriends(new Date(), playerOneId,
				playerTwoId);
		return success;
	}
}
