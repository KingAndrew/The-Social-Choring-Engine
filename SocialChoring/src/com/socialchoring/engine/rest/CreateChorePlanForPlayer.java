package com.socialchoring.engine.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;

@Path("/createChorePlanForPlayer")
public class CreateChorePlanForPlayer {
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public long createChorePlanForPlayer(
			@QueryParam("playerId") long playerId,
			@QueryParam("choreId") long choreId) {
		SocialChoringService service = new SocialChoringServiceImpl();
		long chorePlanId = service.createChorePlanForPlayer(playerId, choreId);
		return chorePlanId;
	}
}
