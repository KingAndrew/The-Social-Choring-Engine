<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.sun.jersey.api.client.Client"%>
<%@page import="com.sun.jersey.api.client.ClientResponse"%>
<%@page import="com.sun.jersey.api.client.config.ClientConfig"%>
<%@page import="com.sun.jersey.api.client.WebResource"%>
<%@page import="javax.ws.rs.core.MediaType"%>
<%@page import="com.sun.jersey.spi.container.ContainerRequest"%>
<%@page import="javax.ws.rs.core.UriBuilder"%>
<%@page import="com.sun.jersey.api.client.config.DefaultClientConfig"%>
<%@page import="com.sun.jersey.core.util.Base64"%>

<%
	String userName = (String) request.getSession().getAttribute(
			"userName");
	if (userName == null) {
		response.sendRedirect(request.getContextPath() + "/");
		return;
	}

	ClientConfig config1 = new DefaultClientConfig();
	Client client = Client.create(config1);
	WebResource service = client.resource(UriBuilder.fromUri(
			"http://localhost:8080/SocialChore").build());
	String auth = new String(Base64.encode(userName));
	ClientResponse response1 = service.path("rest")
			.path("getPlayersForAccount").queryParam("accountId", "1")
			.header(ContainerRequest.AUTHORIZATION, "OAuth " + auth)
			.accept(MediaType.APPLICATION_JSON)
			.get(ClientResponse.class);
%>
<%="reponse status: " + response1.getStatus()%>
<%="jason string for getPlayersForAccount: \n"
					+ response1.getEntity(String.class)%>