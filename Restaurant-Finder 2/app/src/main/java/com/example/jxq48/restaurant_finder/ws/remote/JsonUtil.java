package com.example.jxq48.restaurant_finder.ws.remote;

import android.util.Log;

import com.example.jxq48.restaurant_finder.entities.Comment;
import com.example.jxq48.restaurant_finder.entities.Restaurant;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;


/**
 * Created by jxq48 on 7/24/15.
 */
public class JsonUtil {

    //Parse a String in JSON format into a Comment Object.
    public Comment parseCommentfromJson(String CommentString) {
        JSONParser parser = new JSONParser();
        JSONObject objs = null;
        Comment comment = null;
        try {
            objs = (JSONObject) parser.parse(CommentString);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        String comm = (String) objs.get("text");
        String username = (String) objs.get("username");
        Log.d("CommentString is: ",CommentString);

        comment = new Comment(comm, username);
        return comment;
    }
    //Parse a String in JSON format into a Restaurant Object.
    public Restaurant parseRestaurantfromJson(String restString) {
        JSONParser parser=new JSONParser();
        JSONObject objs = null;
        Restaurant restaurant = null;
        try {
            objs = (JSONObject)parser.parse(restString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
            String  restID = (String) objs.get("restID");
            String name = (String) objs.get("name");
            String address = (String) objs.get("address");
            String price = (String) objs.get("price");
            String cuisine= (String) objs.get("cuisine");
            String phone = (String) objs.get("phone");
            String website = (String) objs.get("website");
            String longitude = (String) objs.get("longitude");
            String latitude = (String) objs.get("latitude");

            restaurant = new Restaurant(Integer.valueOf(restID),name,address,price,cuisine,phone,website,Double.valueOf(longitude),Double.valueOf(latitude));

        return restaurant;
    }
}
