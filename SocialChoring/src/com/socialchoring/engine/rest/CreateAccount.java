package com.socialchoring.engine.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;
import com.socialchoring.engine.util.CookieUtil;
import com.sun.jersey.core.util.Base64;

@Path("/createAccount")
public class CreateAccount {
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createAccount(
			@Context HttpServletRequest request,
			@FormParam("parent_first_name") String parent_first_name,
			@FormParam("parent_last_name") String parent_last_name,
			@FormParam("parent_email") String parent_email,
			@FormParam("player_first_name") String player_first_name) {
		String authentication = CookieUtil.getCookieValue(request.getCookies(), "SocialChoreCookie");
		if (authentication == null) {
			System.err.println("no authentication info found");
		}
		String username = new String(Base64.base64Decode(authentication));
		SocialChoringService service = new SocialChoringServiceImpl();
		long id = service.createAccount(username, parent_first_name, parent_last_name,
				parent_email, player_first_name);
		if (id <= 0)
			throw new RuntimeException("Get: Create Account Failed.");
		
		return String.valueOf(id);
	}
}
