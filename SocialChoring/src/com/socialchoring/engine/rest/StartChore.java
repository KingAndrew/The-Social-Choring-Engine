package com.socialchoring.engine.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;

@Path("/startChore")
public class StartChore {
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String startChore(@FormParam("chorePlanId") long chorePlanId, @FormParam("timeStarted") long timeStarted) {
		SocialChoringService service = new SocialChoringServiceImpl();
		long choreObservedId = service.startChore(chorePlanId, timeStarted, new Date());
		return String.valueOf(choreObservedId);
	}
}
