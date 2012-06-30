package com.socialchoring.engine.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;
import com.socialchoring.engine.util.CookieUtil;
import com.sun.jersey.core.util.Base64;

@Path("/getAccountForUser")
public class GetAccountForUser {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public long getAccountForUser(
			@Context HttpServletRequest request) {
		String authentication = CookieUtil.getCookieValue(request.getCookies(), "SocialChoreCookie");
		if (authentication == null) {
			System.err.println("no authentication info found");
		}
		String username = new String(Base64.base64Decode(authentication));
		SocialChoringService service = new SocialChoringServiceImpl();
		long parent = service.getAccountForUser(username);
		if (parent <= 0)
			throw new RuntimeException("Get: User " + username + " has no account found");
		return parent;
	}
}
