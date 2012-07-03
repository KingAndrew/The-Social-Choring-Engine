<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.beans.factory.config.AutowireCapableBeanFactory"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.socialchoring.engine.service.SocialChoringService"%>


<%
	boolean verified = false;
	String userName = null;
	String authentication = com.socialchoring.engine.util.CookieUtil.getCookieValue(request.getCookies(), "SocialChoreCookie");
	if (authentication != null) {
		userName = new String(com.sun.jersey.core.util.Base64.base64Decode(authentication));
		
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
		SocialChoringService service = (SocialChoringService) autowireCapableBeanFactory.getBean("socialChoringServiceImpl");

		verified = service.verifyUser(userName);
	}
	if (!verified) {
%>
Welcome! Please
<a href="/SocialChore/signin">Login through twitter</a>
<%
	} else {
%>

Welcome!

<%=userName%>


You can try
<a href="/SocialChore/getPlayersForAccount.jsp">GetPlayersForAccount
	with accountId=1</a>
<%
	}
%>

