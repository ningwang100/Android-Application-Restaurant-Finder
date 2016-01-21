package com.prgguru.jersey;

import java.io.IOException;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;


public interface BusinessLogicServer {
    public String getComments(@QueryParam("restID") String restID);
    public String insertComment(@QueryParam("restID") String restID, @QueryParam("comment") String comment, @QueryParam("username") String username);
    public String getFavorites(@QueryParam("username") String username) throws IOException;
    public String deleteFavorite(@QueryParam("username") String username, @QueryParam("restID") String restID);
    public String addFavorite(@QueryParam("username") String username, @QueryParam("restID") String restID);
    public String isValid(@QueryParam("username") String username, @QueryParam("password") String password);
    public String insertUser(@QueryParam("username") String username,@QueryParam("password") String password,@QueryParam("name") String name,@QueryParam("age") String age);
    public String getRestaurantsByName(@QueryParam("restName") String restName);
    public String getRestaurantsByAddress(@QueryParam("restAddress") String restAddress);
    public String getRestaurantsByCuisine(@QueryParam("restCuisine") String restCuisine);
    public String initialDB();
    public String getNearbyRestaurant(@QueryParam("longitude") String longitude, @QueryParam("latitude") String latitude);
    public Response getImage(@QueryParam("restID") String restID);
    public String getPhotoNum();
	public Response getPhoto(@QueryParam("number") String number);

}
