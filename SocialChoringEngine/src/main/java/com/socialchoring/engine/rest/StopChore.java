package com.socialchoring.engine.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;

@Path("/stopChore")
public class StopChore {
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String stopChore(
			@FormParam("choreObservedId") long choreObservedId,
			@FormParam("timeStopped") long timeStopped,
			@FormParam("isComplated") boolean isComplated) {
		SocialChoringService service = new SocialChoringServiceImpl();
		boolean success = service.stopChore(choreObservedId, timeStopped,
				isComplated);
		return String.valueOf(success);
	}
}
