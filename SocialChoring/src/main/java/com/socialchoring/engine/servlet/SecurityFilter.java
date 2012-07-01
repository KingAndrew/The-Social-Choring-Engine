package com.socialchoring.engine.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.service.SocialChoringServiceImpl;
import com.socialchoring.engine.util.CookieUtil;
import com.sun.jersey.core.util.Base64;

public class SecurityFilter implements Filter {

	/**
	 * Perform the required authentication checks, and return the verification
	 * result.
	 * 
	 * @param servletRequest
	 * 
	 */
	private boolean authenticate(HttpServletRequest servletRequest) {
		String authentication = CookieUtil.getCookieValue(servletRequest.getCookies(), "SocialChoreCookie");
		if (authentication == null) {
			System.err.println("no authentication info found");
			return false;
		}
		// if (!authentication.startsWith("OAuth ")) {
		// return false;
		// // additional checks should be done here
		// // "Only HTTP Basic authentication is supported"
		// }
		// authentication = authentication.substring("OAuth ".length());
		String username = new String(Base64.base64Decode(authentication));
		SocialChoringService service = new SocialChoringServiceImpl();
		boolean verified = service.verifyUser(username);
		if (verified) {
			System.out.println("USER AUTHENTICATED");
			// } else if (username.equals("admin") &&
			// password.equals("adminadmin")) {
			// user = new User("admin", "admin");
			// System.out.println("ADMIN AUTHENTICATED");
		} else {
			System.out.println("USER NOT AUTHENTICATED");
		}
		return verified;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * Authenticate the user for this request, and add a security context so
	 * that role checking can be performed.
	 * 
	 * @param request
	 *            The request we re processing
	 * @return the decorated request
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		boolean verified = authenticate(servletRequest);
		if (verified) {
			filterChain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendRedirect(servletRequest.getContextPath() + "/");

		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}