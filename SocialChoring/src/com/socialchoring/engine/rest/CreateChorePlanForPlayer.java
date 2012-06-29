package com.socialchoring.engine.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;

@Path("/createChorePlanForPlayer")
public class CreateChorePlanForPlayer {
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createChorePlanForPlayer(
			@FormParam("playerId") long playerId,
			@FormParam("choreId") long choreId) {
		SocialChoringService service = new SocialChoringServiceImpl();
		long chorePlanId = service.createChorePlanForPlayer(playerId, choreId);
		return String.valueOf(chorePlanId);
	}
}
