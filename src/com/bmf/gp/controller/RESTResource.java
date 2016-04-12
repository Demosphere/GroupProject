package com.bmf.gp.controller;
import com.bmf.gp.interfaces.HTTPHeaderNames;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.security.GeneralSecurityException;

/**
 * Created by Brendon on 4/5/2016.
 */
@Path( "/auth-resource")
public class RESTResource {
    
    private static final long serialVersionUID = -6663599014192066936L;

    @POST
    @Path( "login" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response login(
            @Context HttpHeaders httpHeaders,
            @FormParam("username") String username,
            @FormParam("password") String password) {

        Authenticator authenticator = Authenticator.getInstance();
        String serviceKey = httpHeaders.getHeaderString( HTTPHeaderNames.SERVICE_KEY );

        try {
            String authToken = authenticator.login( serviceKey, username, password );

            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "auth_token", authToken );
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();

        } catch ( final LoginException ex ) {
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", "Problem matching service key, username and password" );
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        }
    }

    @POST
    @Path( "subscribe" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response subscribe(
            @Context HttpHeaders httpHeaders,
            @FormParam("username") String username,
            @FormParam("password") String password) {

        Authenticator authenticator = Authenticator.getInstance();
        String serviceKey = httpHeaders.getHeaderString( HTTPHeaderNames.SERVICE_KEY );
        String jsonMessage;
        Response.Status jsonStatus;

        try {
            int returnMessage = authenticator.subscribeUser( serviceKey, username, password );

            if (returnMessage == 100) {
                jsonMessage = "User Added";
                jsonStatus = Response.Status.OK;

            } else if (returnMessage == -805) {
                jsonMessage = "Username has been taken already for site.";
                jsonStatus = Response.Status.FOUND;

            } else {
                jsonMessage = "Issue with site authenticatoin.";
                jsonStatus = Response.Status.UNAUTHORIZED;

            }

            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", jsonMessage );
            JsonObject jsonObj = jsonObjBuilder.build();
            return getNoCacheResponseBuilder( jsonStatus ).entity( jsonObj.toString() ).build();

        } catch ( final Exception ex ) {
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", "Unknown method exception." );
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder( Response.Status.BAD_REQUEST ).entity( jsonObj.toString() ).build();
        }
    }

    @POST
    @Path( "unsubscribe" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response unsubscribe(
            @Context HttpHeaders httpHeaders,
            @FormParam("username") String username,
            @FormParam("password") String password) {

        Authenticator authenticator = Authenticator.getInstance();
        String serviceKey = httpHeaders.getHeaderString( HTTPHeaderNames.SERVICE_KEY );
        String jsonMessage;
        Response.Status jsonStatus;

        try {
            int returnMessage = authenticator.unSubscribeUser( serviceKey, username, password );

            if (returnMessage == 100) {
                jsonMessage = "User Removed";
                jsonStatus = Response.Status.OK;

            } else if (returnMessage == -805) {
                jsonMessage = "Username was not found for this site.";
                jsonStatus = Response.Status.NOT_FOUND;

            } else {
                jsonMessage = "Issue with site authenticatoin.";
                jsonStatus = Response.Status.UNAUTHORIZED;

            }

            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", jsonMessage );
            JsonObject jsonObj = jsonObjBuilder.build();
            return getNoCacheResponseBuilder( jsonStatus ).entity( jsonObj.toString() ).build();

        } catch ( final Exception ex ) {
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", "Unknown method exception." );
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder( Response.Status.BAD_REQUEST ).entity( jsonObj.toString() ).build();
        }
    }

    @POST
    @Path( "logout" )
    public Response logout(
            @Context HttpHeaders httpHeaders ) {
        try {
            Authenticator authenticator = Authenticator.getInstance();
            String serviceKey = httpHeaders.getHeaderString( HTTPHeaderNames.SERVICE_KEY );
            String authToken = httpHeaders.getHeaderString( HTTPHeaderNames.AUTH_TOKEN );

            authenticator.logout( serviceKey, authToken );

            return getNoCacheResponseBuilder( Response.Status.NO_CONTENT ).build();
        } catch ( final GeneralSecurityException ex ) {
            return getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).build();
        }
    }

    private Response.ResponseBuilder getNoCacheResponseBuilder( Response.Status status ) {
        CacheControl cc = new CacheControl();
        cc.setNoCache( true );
        cc.setMaxAge( -1 );
        cc.setMustRevalidate( true );

        return Response.status( status ).cacheControl( cc );
    }
    
}
