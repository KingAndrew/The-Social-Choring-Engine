package com.socialchoring.engine.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.socialchoring.engine.service.SocialChoringService;
import com.socialchoring.engine.util.CookieUtil;
import com.sun.jersey.core.util.Base64;

@Component
public class CallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1657390011452788111L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
		RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
		String verifier = request.getParameter("oauth_verifier");
		String denied = request.getParameter("denied");
		if (denied != null) {
			response.sendRedirect(request.getContextPath() + "/accessdeny.jsp");
			return;
		}
		try {
			AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
			// request.getSession().setAttribute("accessToken",
			// accessToken.getToken());
			// request.getSession().setAttribute("userName",
			// accessToken.getScreenName() + "@Twitter");
			// request.getSession().setAttribute("userId",
			// accessToken.getUserId());
			request.getSession().removeAttribute("requestToken");

			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

			AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
			SocialChoringService socialChoringServiceImpl = (SocialChoringService) autowireCapableBeanFactory.getBean("socialChoringServiceImpl");

			boolean success = socialChoringServiceImpl.login(accessToken.getScreenName() + "@Twitter", accessToken.getUserId(), new Date());
			if (!success) {
				response.sendRedirect(request.getContextPath() + "/signin");
				return;
			}
			response.addCookie(CookieUtil.getCookie(request.getCookies(), "SocialChoreCookie", new String(Base64.encode(accessToken.getScreenName() + "@Twitter"))));
		} catch (TwitterException e) {
			throw new ServletException(e);
		}
		response.sendRedirect(request.getContextPath() + "/");
	}

}