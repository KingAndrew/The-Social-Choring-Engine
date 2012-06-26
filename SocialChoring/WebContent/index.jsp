<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>



<%
Long userId = (Long) request.getSession().getAttribute("userId");
String userName = (String) request.getSession().getAttribute("userName");

com.socialchoring.service.SocialChoringService service = new com.socialchoring.service.SocialChoringServiceImpl();
boolean verified = service.verifyUser(userName);
if(!verified || userName==null){
%>
	Welcome! Please <a href="/SocialChore/signin">Login through twitter</a>
<%
}
else{
	
%>

Welcome! 
	
<%=userName%>


	You can try <a href="/SocialChore/getPlayersForAccount.jsp">GetPlayersForAccount with accountId=1</a>
<%
}
%>

