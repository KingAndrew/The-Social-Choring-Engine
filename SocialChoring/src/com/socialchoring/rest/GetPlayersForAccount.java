package com.socialchoring.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.socialchoring.bean.Player;
import com.socialchoring.service.SocialChoringService;
import com.socialchoring.service.SocialChoringServiceImpl;

@Path("/getPlayersForAccount")
public class GetPlayersForAccount {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Player> getTodo(@QueryParam("accountId") long accountId) {
		// Principal p = sc.getUserPrincipal();
		// if (!sc.isUserInRole("role")) {
		// System.err.println("user not login........");
		// }

		SocialChoringService service = new SocialChoringServiceImpl();
		List<Player> players = service.getPlayersForAccount(accountId);
		if (players == null)
			throw new RuntimeException("Get: Account with " + accountId
					+ " not found");
		return players;
	}
}
