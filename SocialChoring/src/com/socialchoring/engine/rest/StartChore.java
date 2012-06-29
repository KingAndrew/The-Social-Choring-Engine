package com.socialchoring.engine.rest;

import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;

@Path("/startChore")
public class StartChore {
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public long startChore(@QueryParam("chorePlanId") long chorePlanId,
			@QueryParam("timeStarted") long timeStarted) {
		SocialChoringService service = new SocialChoringServiceImpl();
		long choreObservedId = service.startChore(chorePlanId, timeStarted,
				new Date());
		return choreObservedId;
	}
}
