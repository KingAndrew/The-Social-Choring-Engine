package com.socialchoring.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.socialchoring.bean.Player;
import com.socialchoring.service.SocialChoringService;
import com.socialchoring.service.SocialChoringServiceImpl;

@Path("/createAccount")
public class CreateAccount {
	// This method is called if XMLis request
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Player createAccount(
			@PathParam("parent_first_name") String parent_first_name,
			@PathParam("parent_last_name") String parent_last_name,
			@PathParam("parent_email") String parent_email,
			@PathParam("player_first_name") String player_first_name) {

		SocialChoringService service = new SocialChoringServiceImpl();
		long id = service.createAccount(parent_first_name, parent_last_name,
				parent_email, player_first_name);

		List<Player> players = null;
		if (id > 0) {
			players = service.getPlayersForAccount(id);
		}
		if (players != null && !players.isEmpty()) {
			return players.get(0);
		} else
			return null;

	}
}
