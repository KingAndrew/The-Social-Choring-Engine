package com.socialchoring.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.socialchoring.service.SocialChoringService;
import com.socialchoring.service.SocialChoringServiceImpl;

@Path("/createChorePlanForPlayer")
public class CreateChorePlanForPlayer {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public long createChorePlanForPlayer(
			@QueryParam("playerId") long playerId,
			@QueryParam("choreId") long choreId) {
		SocialChoringService service = new SocialChoringServiceImpl();
		long chorePlanId = service.createChorePlanForPlayer(playerId, choreId);
		return chorePlanId;
	}
}
