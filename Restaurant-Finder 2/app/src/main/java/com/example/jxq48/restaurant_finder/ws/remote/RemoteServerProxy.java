package com.example.jxq48.restaurant_finder.ws.remote;
import com.example.jxq48.restaurant_finder.entities.Comment;
import com.example.jxq48.restaurant_finder.entities.Restaurant;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
/**
 * Created by jxq48 on 7/18/15.
 */
public class RemoteServerProxy implements CommentsProxy, RestaurantsProxy, UsersProxy, ImagesProxy {
   //Please change TESTIP to your current IP address.
    public final static String TESTIP = "10.0.0.7";
    private final String ipAddress = "http://"+TESTIP+":8080/RestaurantFinderServer/MyServer/";

    //Initialize database information
    public String initialDB() {
        String targetURL = ipAddress + "initialDB";
        String resp = "";
        try {
            resp = new RestfulPostService().execute(targetURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    //Get comments of a restaurant from database given by a restaurant ID
    public ArrayList<Comment> getComments(int restID) {
        String targetURL = ipAddress+"getComments?restID="+restID;
        JsonUtil jsonUtil = new JsonUtil();
        ArrayList<Comment> comments = new ArrayList<>();
        String resp = "";
        try {
            resp = new RestfulGetService().execute(targetURL).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Object objRests = JSONValue.parse(resp);
        JSONArray arrayRests=(JSONArray)objRests;
        for (int i = 0; i < arrayRests.size(); i++) {
            comments.add(jsonUtil.parseCommentfromJson(arrayRests.get(i).toString()));
        }
        return comments;
    }

    //Insert a user into database
    public String insertUser(String username, String password, String name, String age) {

        String targetURL = ipAddress+"insertUser?username="+username+"&password="+password+"&name="+name+"&age="+age;
        String resp = "";
        try {
            resp = new RestfulPostService().execute(targetURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    //Judge whether a username and password is matched in database
    public String isValid(String username, String password) {
        String targetURL = ipAddress+"isValid?username="+username+"&password="+password;
        String resp = null;
        try {
            resp = new RestfulPostService().execute(targetURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    //Get a user's favorite restaurant list based on an username
    public ArrayList<Restaurant> getFavorites(String username) {
        String targetURL = ipAddress+"getFavorites?username="+username;
        String resp = "";
        JsonUtil jsonUtil = new JsonUtil();
        ArrayList<Restaurant> list = new ArrayList<>();
        try {
        resp = new RestfulGetService().execute(targetURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Object objRests = JSONValue.parse(resp);
        JSONArray arrayRests=(JSONArray)objRests;
        for (int i = 0; i < arrayRests.size(); i++) {
            list.add(jsonUtil.parseRestaurantfromJson(arrayRests.get(i).toString()));
        }
        return list;
    }

    //Insert a comment to database given by a text, username and the commented restaurant
    public String addComment(int restID, String text, String username) {
        String targetURL = ipAddress + "insertComment?restID="+restID+"&comment="+text+"&username="+username;
        String resp = "";
        try {
            resp = new RestfulPostService().execute(targetURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    //Delete a restaurant from a user's favorite list
    public String deleteFavorite(String username, int restID) {
        String targetURL = ipAddress + "deleteFavorite?username="+username+"&restID="+restID;
        String resp = "";
        try {
            resp = new RestfulPostService().execute(targetURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    //Add a restaurant to a user's favorite list
    public String addFavorite(String username, int restID) {
        String targetURL = ipAddress + "addFavorite?username="+username+"&restID="+restID;
        String resp = "";
        try {
            resp = new RestfulPostService().execute(targetURL).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    //Search out a list of restaurants based on a restaurant name
    public ArrayList<Restaurant> getRestaurantsByName(String restName) {
        String targetURL = ipAddress + "getRestaurantsByName?restName="+restName;
        String resp = "";
        JsonUtil jsonUtil = new JsonUtil();
        ArrayList<Restaurant> list = new ArrayList<>();
        try {
            resp = new RestfulGetService().execute(targetURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Object objRests = JSONValue.parse(resp);
        JSONArray arrayRests=(JSONArray)objRests;
        for (int i = 0; i < arrayRests.size(); i++) {
            list.add(jsonUtil.parseRestaurantfromJson(arrayRests.get(i).toString()));
        }
        return list;
    }

    //Search out a list of restaurants based on a restaurant address
    public ArrayList<Restaurant> getRestaurantsByAddress(String restAddress) {
        String targetURL = ipAddress + "getRestaurantsByAddress?restAddress="+restAddress;
        String resp = "";
        JsonUtil jsonUtil = new JsonUtil();
        ArrayList<Restaurant> list = new ArrayList<>();
        try {
            resp = new RestfulGetService().execute(targetURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Object objRests = JSONValue.parse(resp);
        JSONArray arrayRests=(JSONArray)objRests;
        for (int i = 0; i < arrayRests.size(); i++) {
            list.add(jsonUtil.parseRestaurantfromJson(arrayRests.get(i).toString()));
        }
        return list;
    }

    //Search out a list of restaurants based on a restaurant cuisine
    public ArrayList<Restaurant> getRestaurantsByCuisine(String restCuisine) {
        String targetURL = ipAddress + "getRestaurantsByCuisine?restCuisine="+restCuisine;
        String resp = "";
        JsonUtil jsonUtil = new JsonUtil();
        ArrayList<Restaurant> list = new ArrayList<>();
        try {
            resp = new RestfulGetService().execute(targetURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Object objRests = JSONValue.parse(resp);
        JSONArray arrayRests=(JSONArray)objRests;
        for (int i = 0; i < arrayRests.size(); i++) {
            list.add(jsonUtil.parseRestaurantfromJson(arrayRests.get(i).toString()));
        }
        return list;
    }

    //Get a list of restaurants whose distance is less than 2000 meter from user's current location
    public ArrayList<Restaurant> getNearbyRestaurant(double longitude, double latitude ) {
        String targetURL = ipAddress + "getNearbyRestaurant?longitude="+longitude+"&latitude="+latitude;
        String resp = "";
        JsonUtil jsonUtil = new JsonUtil();
        ArrayList<Restaurant> list = new ArrayList<>();
        try {
            resp = new RestfulGetService().execute(targetURL).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Object objRests = JSONValue.parse(resp);
        JSONArray arrayRests=(JSONArray)objRests;
        for (int i = 0; i < arrayRests.size(); i++) {
            list.add(jsonUtil.parseRestaurantfromJson(arrayRests.get(i).toString()));
        }
        return list;
    }

    //Get one restaurant image from sample folder
    public ArrayList<String> getImageFromServer(String restID) {
        String targetURL = ipAddress + "getImage?restID=" + restID;
        ArrayList<String> resp = null;
        try {
            resp = (ArrayList<String>) new RestfulGetImgService().execute(restID, targetURL).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return resp;
    }

    //Upload one photo from client to server
    public void uploadImage(String filePath, String fileLength) {
        try {
            String rst = new RestfulPostImgService().execute(filePath, fileLength).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
        }
    }
    //Get number of photos taken by all users
    public String getPhotoNum(String username) {
        String targetURL = ipAddress + "getPhotoNum?username=" + username;
        String resp = null;
        try {
            resp = new RestfulPostService().execute(targetURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }
    //Get one photo taken by user before, from photos folder
    public ArrayList<String> getPhotoFromServer(String username, String number) {
        String targetURL = ipAddress + "getPhoto?username=" + username + "&number=" + number;
        ArrayList<String> resp = null;
        try {
            resp = (ArrayList<String>) new RestfulGetPhotoService().execute(username, targetURL, number).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return resp;
    }
}
