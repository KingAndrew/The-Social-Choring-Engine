package com.socialchoring.engine.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;

@Path("/stopChore")
public class StopChore {
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public boolean stopChore(
			@QueryParam("choreObservedId") long choreObservedId,
			@QueryParam("timeStopped") long timeStopped,
			@QueryParam("isComplated") boolean isComplated) {
		SocialChoringService service = new SocialChoringServiceImpl();
		boolean success = service.stopChore(choreObservedId, timeStopped,
				isComplated);
		return success;
	}
}
