/*
 * Copyright (c) 2009 Sun Microsystems, Inc.  All rights reserved.  U.S.
 * Government Rights - Commercial software.  Government users are subject
 * to the Sun Microsystems, Inc. standard license agreement and
 * applicable provisions of the FAR and its supplements.  Use is subject
 * to license terms.
 *
 * This distribution may include materials developed by third parties.
 * Sun, Sun Microsystems, the Sun logo, Java and J2EE are trademarks
 * or registered trademarks of Sun Microsystems, Inc. in the U.S. and
 * other countries.
 *
 * Copyright (c) 2009 Sun Microsystems, Inc. Tous droits reserves.
 *
 * Droits du gouvernement americain, utilisateurs gouvernementaux - logiciel
 * commercial. Les utilisateurs gouvernementaux sont soumis au contrat de
 * licence standard de Sun Microsystems, Inc., ainsi qu'aux dispositions
 * en vigueur de la FAR (Federal Acquisition Regulations) et des
 * supplements a celles-ci.  Distribue par des licences qui en
 * restreignent l'utilisation.
 *
 * Cette distribution peut comprendre des composants developpes par des
 * tierces parties. Sun, Sun Microsystems, le logo Sun, Java et J2EE
 * sont des marques de fabrique ou des marques deposees de Sun
 * Microsystems, Inc. aux Etats-Unis et dans d'autres pays.
 */
package com.sun.samples;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;


/**
 * Sample servlet that demonstrates OAuth token support with OpenSSO
 * on Glassfish
 */
public class GetQuote extends HttpServlet {

    private static final String TOKEN_SERVICE =
        "http://localhost:8080/TokenService/resources/oauth/v1/";
    private static final String CONSUMER_NAME = "StockClient";
    private static final String CONSUMER_KEY =
                         TOKEN_SERVICE + "consumer/" + CONSUMER_NAME;
    private static final String CONSUMER_SECRET = CONSUMER_NAME + "_secret";
    private static final String SIGNATURE_METHOD = "HMAC-SHA1";
    private static final Client client = Client.create();
    private static String oauth_token_str = null;
    private static String oauth_secret_str = null;

    String url = 
        "http://localhost:8080/StockServiceREST/resources/stock?quote=";
    
    
    /** Processes requests for both HTTP <code>GET</code> and 
     * <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, 
           HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String symbol = request.getParameter("symbol");
        if ((symbol == null) || (symbol.length() == 0)) {
            out.println("<h1>Invalid Stock Symbol</h1>");
            out.close();
            return;
        }
        
        try {
            //Check for the call back parameter  and if yes
            // obtain the access token.
            String cb = request.getParameter("callback");
            if(cb != null && cb.equals("true")) {
               String reqtoken = request.getParameter("oauth_token");
               MultivaluedMap accessTokenResp = getAccessToken();
               String accessToken = (String)accessTokenResp.getFirst(
                          "oauth_token");
               String accessTokenSecret = (String)accessTokenResp.getFirst(
                               "oauth_token_secret");
               //Reinitialize the params and secret.
               OAuthParameters params = initOAuthParams();
               params.token(accessToken);
               OAuthSecrets secrets = initOAuthSecrets();
               secrets.tokenSecret(accessTokenSecret);
               //now access the resource
              OAuthClientFilter filter = getClientFilter(params, secrets);
              WebResource resource = client.resource(url + symbol);
              resource.addFilter(filter);
              ClientResponse clr = resource.get(ClientResponse.class); 
              response.setContentType("text/xml;charset=UTF-8");
              out.println(clr.getEntity(String.class));
              return;
            }
            // Register the consumer. This can also be done offband.
            registerConsumer();
            // Get StockQuote
            WebResource resource = client.resource(url + symbol);
            ClientResponse clr = resource.get(ClientResponse.class);
            if(clr.getResponseStatus().getStatusCode() == 401) {
               //unauthorized
               MultivaluedMap rtresp = getRequestToken();
               //out.println("RT resp" + rtresp);
              redirectForAuthentication(request, response, rtresp, symbol);
            } 
            //out.println(clr.getEntity(String.class));
        } catch (Exception ex) {
            ex.printStackTrace(out);
        }
        out.close();
    }

    private MultivaluedMap getRequestToken() {
        OAuthParameters params = initOAuthParams();
        OAuthSecrets secrets = initOAuthSecrets();
        OAuthClientFilter filter = getClientFilter(params, secrets);
        WebResource resource = client.resource(
                TOKEN_SERVICE + "get_request_token");
        resource.addFilter(filter);
        MultivaluedMap<String, String> response = 
                  POST(resource, new MultivaluedMapImpl());
        oauth_token_str = (String)response.getFirst("oauth_token");
        oauth_secret_str = (String)response.getFirst("oauth_token_secret");
        return response;

    }

    private MultivaluedMap getAccessToken() {
        OAuthParameters params = initOAuthParams();
        OAuthSecrets secrets = initOAuthSecrets();
        if(oauth_token_str != null) {
           params.token(oauth_token_str);
        }
        secrets.tokenSecret(oauth_secret_str);
        OAuthClientFilter filter = getClientFilter(params, secrets);
        WebResource resource = client.resource(
                TOKEN_SERVICE + "get_access_token");
        resource.addFilter(filter);
        MultivaluedMap<String, String> response = POST(resource, 
            new MultivaluedMapImpl());
        return response;
    }

    private OAuthSecrets initOAuthSecrets() {
          OAuthSecrets secrets = new OAuthSecrets();
          secrets.consumerSecret(CONSUMER_SECRET);
          return secrets;
    }

    private OAuthParameters initOAuthParams() {
        OAuthParameters params = new OAuthParameters();
        params.consumerKey(CONSUMER_KEY).signatureMethod(SIGNATURE_METHOD);
        return params;
    }

    private OAuthClientFilter getClientFilter(OAuthParameters params,
         OAuthSecrets secrets) {
        OAuthClientFilter filter = new OAuthClientFilter(
                         client.getProviders(), params, secrets);
        return filter;
    }


    @SuppressWarnings("unchecked")
    private static MultivaluedMap<String, String> POST(
             WebResource resource, MultivaluedMap data) {
        return resource.type("application/x-www-form-urlencoded").post(
               MultivaluedMap.class, data);
    }

    private void redirectForAuthentication(HttpServletRequest request,
        HttpServletResponse response, MultivaluedMap rtResp,
        String symbol) throws Exception {
        String oauthtoken = (String)rtResp.getFirst("oauth_token");
        System.out.println("OAuth token: " + oauthtoken);
 
        String url = "http://localhost:8080/TokenService/authenticate.jsp?" +
              "username=demo" + "&" +
              "password=changeit" + "&" +
              "url=http://localhost:8080/opensso/identity" + "&" +
              "oauth_token=" + oauthtoken + "&" +
              "oauth_callback=" + 
              java.net.URLEncoder.encode("http://localhost:8080/StockClientREST/GetQuote?callback=true&symbol=" + symbol);
          response.sendRedirect(url);

    }

    private static void registerConsumer() {

        WebResource resource = client.resource(TOKEN_SERVICE +
               "consumer_registration");
        MultivaluedMapImpl form = new MultivaluedMapImpl();
        form.add("cons_key", CONSUMER_KEY);
        form.add("secret", CONSUMER_SECRET);
        form.add("name", CONSUMER_NAME);
        form.add("signature_method", "HMAC-SHA1");

        MultivaluedMap response = POST(resource, form);

    }
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
