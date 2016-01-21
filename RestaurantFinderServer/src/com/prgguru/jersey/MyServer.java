package com.prgguru.jersey;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import dbLayout.DataBaseHandler;
import dbLayout.Restaurant;
import entities.Comment;
import entities.RestaurantString;
import entities.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/MyServer")
public class MyServer implements BusinessLogicServer {
	public final static String DIRECTIORY = "/Users/jxq48/Desktop/";
	
	//Get comments of a restaurant from database given by a restaurant ID
	@GET
	@Path("/getComments")
	@Produces(MediaType.APPLICATION_JSON)
	public String getComments(@QueryParam("restID") String restID) {
		DataBaseHandler db = new DataBaseHandler();
		ArrayList<String> CommentString = new ArrayList<String>();
		ArrayList<Comment> list = db.getCommentList(Integer.valueOf(restID));
		for (int i = 0; i < list.size(); i++) {
			CommentString.add(list.get(i).toString());
		}

		return CommentString.toString();
	}

	//Insert a comment to database given by a text, username and the commented restaurant
	@POST
	@Path("/insertComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertComment(@QueryParam("restID") String restID,
			@QueryParam("comment") String comment1, @QueryParam("username") String username) {
		String comment = comment1.replaceAll("TOBEDELETE", " ").replace("DELETETO", "\n");
		DataBaseHandler db = new DataBaseHandler();
		if (db.addComment(Integer.valueOf(restID), comment, username))
			return "success";
		return "fail";
	}

	//Get a user's favorite restaurant list based on an username
	@GET
	@Path("/getFavorites")
	@Produces(MediaType.APPLICATION_JSON)
	public String getFavorites(@QueryParam("username") String username)
			throws IOException {
		DataBaseHandler db = new DataBaseHandler();
		ArrayList<Restaurant> list = db.getFavoriteList(username);
		ArrayList<RestaurantString> list2 = new ArrayList<RestaurantString>();
		for (int i = 0; i < list.size(); i++) {
			Restaurant tmp = list.get(i);
			RestaurantString tmp2 = new RestaurantString(tmp.getRestID(),
					tmp.getName(), tmp.getAddress(), tmp.getPrice(),
					tmp.getCuisine(), tmp.getPhone(), tmp.getWebsite(),
					tmp.getLongitude(), tmp.getLatitude());
			list2.add(tmp2);
		}
		ArrayList<String> restString = new ArrayList<String>();
		for (int i = 0; i < list2.size(); i++)
			restString.add(list2.get(i).toString());
		System.out.println(restString.toString());
		return restString.toString();
	}

	//Delete a restaurant from a user's favorite list
	@POST
	@Path("/deleteFavorite")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteFavorite(@QueryParam("username") String username,
			@QueryParam("restID") String restID) {
		DataBaseHandler db = new DataBaseHandler();
		if (db.deleteFromFavoriteList(username, Integer.valueOf(restID))) {
			return "success";
		}
		return "fail";
	}
	//Add a restaurant to a user's favorite list
	@POST
	@Path("/addFavorite")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addFavorite(@QueryParam("username") String username,
			@QueryParam("restID") String restID) {
		DataBaseHandler db = new DataBaseHandler();
		System.out.println("!!!!!!!!!!!" + username);
		if (db.addToFavoriteList(username, Integer.valueOf(restID)))
			return "success";
		return "fail";
	}

	//Judge whether a username and password is matched in database
	@POST
	@Path("/isValid")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String isValid(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		DataBaseHandler db = new DataBaseHandler();
		if (db.LoginCheck(username, password))
			return username;
		else
			return "fail";
	}

	//Insert a user into database
	@POST
	@Path("/insertUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertUser(@QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("name") String name, @QueryParam("age") String age) {
		User newUser = new User(username, password, name, age);
		DataBaseHandler db = new DataBaseHandler();
		if (db.addUser(newUser))
			return "success";
		return "fail";
	}

	//Search out a list of restaurants based on a restaurant name
	@GET
	@Path("/getRestaurantsByName")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRestaurantsByName(@QueryParam("restName") String restName1) {
		String restname = restName1.toLowerCase();
		DataBaseHandler db = new DataBaseHandler();
		ArrayList<Restaurant> list = db.getRestaurantsByName(restname);
		ArrayList<RestaurantString> list2 = new ArrayList<RestaurantString>();
		for (int i = 0; i < list.size(); i++) {
			Restaurant tmp = list.get(i);
			RestaurantString tmp2 = new RestaurantString(tmp.getRestID(),
					tmp.getName(), tmp.getAddress(), tmp.getPrice(),
					tmp.getCuisine(), tmp.getPhone(), tmp.getWebsite(),
					tmp.getLongitude(), tmp.getLatitude());
			list2.add(tmp2);
		}
		ArrayList<String> restString = new ArrayList<String>();
		for (int i = 0; i < list2.size(); i++)
			restString.add(list2.get(i).toString());
		return restString.toString();
	}

	//Search out a list of restaurants based on a restaurant address
	@GET
	@Path("/getRestaurantsByAddress")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRestaurantsByAddress(
			@QueryParam("restAddress") String restAddress1) {
		String restAddress = restAddress1.toLowerCase();
		DataBaseHandler db = new DataBaseHandler();
		ArrayList<Restaurant> list = db.getRestaurantsByAddress(restAddress);
		ArrayList<RestaurantString> list2 = new ArrayList<RestaurantString>();
		for (int i = 0; i < list.size(); i++) {
			Restaurant tmp = list.get(i);
			RestaurantString tmp2 = new RestaurantString(tmp.getRestID(),
					tmp.getName(), tmp.getAddress(), tmp.getPrice(),
					tmp.getCuisine(), tmp.getPhone(), tmp.getWebsite(),
					tmp.getLongitude(), tmp.getLatitude());
			list2.add(tmp2);
		}
		ArrayList<String> restString = new ArrayList<String>();
		for (int i = 0; i < list2.size(); i++)
			restString.add(list2.get(i).toString());
		return restString.toString();

	}

	//Search out a list of restaurants based on a restaurant cuisine
	@GET
	@Path("/getRestaurantsByCuisine")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRestaurantsByCuisine(
			@QueryParam("restCuisine") String restCuisine) {
		DataBaseHandler db = new DataBaseHandler();
		ArrayList<Restaurant> list = db.getRestaurantsByCuisine(restCuisine);
		ArrayList<RestaurantString> list2 = new ArrayList<RestaurantString>();
		for (int i = 0; i < list.size(); i++) {
			Restaurant tmp = list.get(i);
			RestaurantString tmp2 = new RestaurantString(tmp.getRestID(),
					tmp.getName(), tmp.getAddress(), tmp.getPrice(),
					tmp.getCuisine(), tmp.getPhone(), tmp.getWebsite(),
					tmp.getLongitude(), tmp.getLatitude());
			list2.add(tmp2);
		}
		ArrayList<String> restString = new ArrayList<String>();
		for (int i = 0; i < list2.size(); i++)
			restString.add(list2.get(i).toString());
		return restString.toString();
	}

	//Initialize database information
	@POST
	@Path("/initialDB")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String initialDB() {
		DataBaseHandler db = new DataBaseHandler();
		db.initializeDatabase();

		return "success";
	}

	//Get a list of restaurants whose distance is less than 2000 meter from user's current location
	@GET
	@Path("/getNearbyRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public String getNearbyRestaurant(
			@QueryParam("longitude") String longitude,
			@QueryParam("latitude") String latitude) {
		DataBaseHandler db = new DataBaseHandler();
		ArrayList<Restaurant> list = db.getAllRestaurant();

		ArrayList<RestaurantString> list2 = new ArrayList<RestaurantString>();
		for (int i = 0; i < list.size(); i++) {
			Restaurant tmp = list.get(i);
			double tmpLog = tmp.getLongitude();
			double tmpLat = tmp.getLatitude();
			double dis = Distance.GetDistance(tmpLog, tmpLat,
					Double.valueOf(longitude), Double.valueOf(latitude));
			if (dis < 2000) {
				RestaurantString tmp2 = new RestaurantString(tmp.getRestID(),
						tmp.getName(), tmp.getAddress(), tmp.getPrice(),
						tmp.getCuisine(), tmp.getPhone(), tmp.getWebsite(),
						tmp.getLongitude(), tmp.getLatitude());
				list2.add(tmp2);
			}
		}
		ArrayList<String> restString = new ArrayList<String>();
		for (int i = 0; i < list2.size(); i++)
			restString.add(list2.get(i).toString());
		return restString.toString();
	}

	//Get number of photos taken by all users
	@POST
	@Path("/getPhotoNum")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getPhotoNum() {
		File file=new File(DIRECTIORY+"photos");
		String test[];
		test=file.list();
		if (test!=null && test.length != 0)
			return String.valueOf(test.length);
		else 
			return "0";
	}
	
	//Get one restaurant image from sample folder
	@GET
	@Path("/getImage")
	@Produces("image/png")
	public Response getImage(@QueryParam("restID") String restID) {
		String photoName = "sample" + restID + ".png";
		File file = new File(DIRECTIORY+"sample/" + photoName);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("imgFrom", photoName);
		Response resp = response.build();
		return resp;

	}

	//Get one photo taken by user before, from photos folder
	@GET
	@Path("/getPhoto")
	@Produces("image/png")
	public Response getPhoto(@QueryParam("number") String number) {
		File fileToDownload = null;
		File file=new File(DIRECTIORY+"photos");
		String test[];
		test=file.list();
		if(test!=null && test.length != 0) {
			fileToDownload = new File(DIRECTIORY +"photos/"+ test[Integer.valueOf(number)]);
			ResponseBuilder response = Response.ok((Object) fileToDownload);
			response.header("imgFrom",
					test[Integer.valueOf(number)]);
			Response resp = response.build();
			System.out.println("get photo!"+number);

			return resp;
		}
		else {
			ResponseBuilder response = Response.noContent();
			return response.build();
		}
	}

}
