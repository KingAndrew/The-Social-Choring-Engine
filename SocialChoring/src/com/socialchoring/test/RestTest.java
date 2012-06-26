package com.socialchoring.test;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import com.sun.jersey.spi.container.ContainerRequest;

@Path("/GetPlayersForAccountOAUTH")
public class RestTest {
	private static final Client client = Client.create();
	private static final String CONSUMER_KEY = "7MSs0y0BR1LZCPePY73g";
	private static final String CONSUMER_SECRET = "8yq4Y2Nx1EdKhLwjLFsYGwG6bwu71RosHMmLgurzw0";
	private static final String SIGNATURE_METHOD = "HMAC-SHA1";

	@Test
	public void testGetPlayersForAccount() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("http://localhost:8080/SocialChore").build());
		ClientResponse response = service.path("rest").path("getPlayersForAccount").queryParam("accountId", "1").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		System.err.println(response.getEntity(String.class));
	}

	@Test
	public void testGetPlayersForAccountBasic() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("http://localhost:8080/SocialChore").build());
		String auth = new String(Base64.encode("username:password"));
		ClientResponse response = service.path("rest").path("getPlayersForAccount").queryParam("accountId", "1").header(ContainerRequest.AUTHORIZATION, "Basic " + auth)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		int statusCode = response.getStatus();
		if (statusCode == 401) {
			System.err.println("Invalid Username or Password");
		}
		System.err.println(response.getEntity(String.class));
	}


	@Test
	public void testGetPlayersForAccountOauth() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("http://localhost:7080/SocialChore").build());
		String auth = new String(Base64.encode("kalparser@Twitter"));
		ClientResponse response = service.path("rest").path("getPlayersForAccount").queryParam("accountId", "1").header(ContainerRequest.AUTHORIZATION, "OAuth " + auth)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		int statusCode = response.getStatus();
		if (statusCode == 401) {
			System.err.println("Invalid Username");
		}
		System.err.println(response.getEntity(String.class));
	}

	// @Test
	public String getRequestToken() {

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.removeAllFilters();

		// Create a resource to be used to make Twitter API calls
		WebResource resource = client.resource("http://twitter.com/oauth/request_token");

		// Set the OAuth parameters
		OAuthSecrets secrets = new OAuthSecrets().consumerSecret("8yq4Y2Nx1EdKhLwjLFsYGwG6bwu71RosHMmLgurzw0");
		OAuthParameters params = new OAuthParameters().consumerKey("7MSs0y0BR1LZCPePY73g").signatureMethod("HMAC-SHA1").version("1.0");
		// Create the OAuth client filter
		OAuthClientFilter oauthFilter = new OAuthClientFilter(client.getProviders(), params, secrets);

		// Add the filter to the resource
		resource.addFilter(oauthFilter);

		// make the request and print out the result
		String response = resource.get(String.class);
		return response;
	}

	private OAuthParameters initOAuthParams() {
		OAuthParameters params = new OAuthParameters();
		params.consumerKey(CONSUMER_KEY).signatureMethod(SIGNATURE_METHOD);
		;
		return params;
	}

	private OAuthSecrets initOAuthSecrets() {
		OAuthSecrets secrets = new OAuthSecrets();
		secrets.consumerSecret(CONSUMER_SECRET);
		return secrets;
	}

	private OAuthClientFilter getClientFilter(OAuthParameters params, OAuthSecrets secrets) {
		OAuthClientFilter filter = new OAuthClientFilter(client.getProviders(), params, secrets);
		return filter;
	}

	@Test
	public void getAccessToken() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.removeAllFilters();

		// Set the OAuth parameters
		OAuthSecrets secrets = new OAuthSecrets().consumerSecret("8yq4Y2Nx1EdKhLwjLFsYGwG6bwu71RosHMmLgurzw0");
		OAuthParameters params = new OAuthParameters().consumerKey("7MSs0y0BR1LZCPePY73g").signatureMethod("HMAC-SHA1").version("1.0")
				.token("wXPKFI6q6qeJeuEtmbtR7qEz9ywyzO8JtsVxqgMR49Y").verifier("0DQpT4PenoIlj4orVK8z3RD50ZXPBFFtPx85egTgC9g");
		// Create the OAuth client filter
		OAuthClientFilter oauthFilter = new OAuthClientFilter(client.getProviders(), params, secrets);

		// Create a resource to be used to make Twitter API calls
		WebResource resource = client.resource("http://api.twitter.com/oauth/access_token");

		// Add the filter to the resource
		resource.addFilter(oauthFilter);
		// make the request and print out the result
		System.out.println(resource.get(String.class));
		// return resource.get(String.class);
	}
}
