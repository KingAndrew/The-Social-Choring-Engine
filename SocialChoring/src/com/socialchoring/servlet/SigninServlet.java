package com.socialchoring.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = -6205814293093350242L;

	String CONSUMER_KEY = "7MSs0y0BR1LZCPePY73g";
	String CONSUMER_SECRET = "8yq4Y2Nx1EdKhLwjLFsYGwG6bwu71RosHMmLgurzw0";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Twitter twitter = new TwitterFactory().getInstance();
		request.getSession().setAttribute("twitter", twitter);
		try {
			StringBuffer callbackURL = request.getRequestURL();
			int index = callbackURL.lastIndexOf("/");
			callbackURL.replace(index, callbackURL.length(), "").append("/callback");
			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
			RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
			request.getSession().setAttribute("requestToken", requestToken);
			response.sendRedirect(requestToken.getAuthenticationURL());

		} catch (TwitterException e) {
			throw new ServletException(e);
		}

	}
}