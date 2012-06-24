package com.socialchoring.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.socialchoring.service.SocialChoringService;
import com.socialchoring.service.SocialChoringServiceImpl;

@Path("/stopChore")
public class StopChore {
	@GET
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
