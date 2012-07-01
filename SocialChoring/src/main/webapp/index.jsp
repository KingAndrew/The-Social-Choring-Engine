<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>



<%
	boolean verified = false;
	String userName = null;
	String authentication = com.socialchoring.engine.util.CookieUtil.getCookieValue(request.getCookies(), "SocialChoreCookie");
	if (authentication != null) {
		userName = new String(com.sun.jersey.core.util.Base64.base64Decode(authentication));
		com.socialchoring.engine.service.SocialChoringService service = new com.socialchoring.engine.service.SocialChoringServiceImpl();
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

