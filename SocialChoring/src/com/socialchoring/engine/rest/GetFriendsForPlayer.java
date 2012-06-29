package com.socialchoring.engine.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.bean.FriendsForDate;
import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;

@Path("/getFriendsForPlayer")
public class GetFriendsForPlayer {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<FriendsForDate> getChoresForPlayerByDate(@QueryParam("playerId") long playerId, @QueryParam("beginDate") Date beginDate) {
		SocialChoringService service = new SocialChoringServiceImpl();
		List<FriendsForDate> friends = service.getFriendsForPlayer(playerId, beginDate);
		if (friends == null)
			throw new RuntimeException("Get: Player with " + playerId + " has no friend found");
		return friends;
	}
}
