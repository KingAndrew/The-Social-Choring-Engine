package com.socialchoring.rest;

import java.security.Principal;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.socialchoring.bean.User;

public class Authorizer implements SecurityContext {

	@Context
	UriInfo uriInfo;

	@Override
	public String getAuthenticationScheme() {
		if (principal == null) {
			return null;
		}
		return SecurityContext.FORM_AUTH;
	}

	@Override
	public Principal getUserPrincipal() {
		return principal;
	}

	@Override
	public boolean isSecure() {
		return "https".equals(uriInfo.getRequestUri().getScheme());
	}

	@Override
	public boolean isUserInRole(String role) {
		//TODO
		return principal != null;
	}

	private Principal principal = null;

	public Authorizer(final User user) {
		if (user != null) {
			principal = new Principal() {
				public String getName() {
					return user.getUserName();
				}
			};
		}
	}

}