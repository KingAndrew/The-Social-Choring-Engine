package com.socialchoring.engine.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.bean.PlayerChorePlan;
import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;

@Path("/getChoresForPlayerByDate")
public class GetChoresForPlayerByDate {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PlayerChorePlan> getChoresForPlayerByDate(@QueryParam("playerId") long playerId, @QueryParam("date") Date date) {
		SocialChoringService service = new SocialChoringServiceImpl();
		List<PlayerChorePlan> plans = service.getChoresForPlayerByDate(playerId, date);
		if (plans == null)
			throw new RuntimeException("Get: Player with " + playerId + " has no chore plan found");
		return plans;
	}
}
