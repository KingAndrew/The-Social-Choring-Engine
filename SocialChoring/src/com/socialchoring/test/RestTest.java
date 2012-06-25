package com.socialchoring.test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.spi.container.ContainerRequest;

public class RestTest {

	@Test
	public void testGetPlayersForAccount() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("http://localhost:7080/SocialChore").build());
		String auth = new String(Base64.encode("username:password"));
		ClientResponse response = service.path("rest").path("getPlayersForAccount").queryParam("accountId", "1").header(ContainerRequest.AUTHORIZATION, "Basic " + auth)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		int statusCode = response.getStatus();
		if (statusCode == 401) {
			System.err.println("Invalid Username or Password");
		}
		System.err.println(response.getEntity(String.class));
	}
}
