/**
 *In this class,it create the Restaurant model which is used to pass restaurant 
 *information from UI and server.
 */
package com.example.jxq48.restaurant_finder.entities;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable {
    private String name;
    private String address;
    private String price;
    private String cuisine;
    private String phone;
    private String website;
    private double longitude;
    private double latitude;
    private int restID;
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    /**
     * Using constructor with arguments to initialize these variables and restaurant object
     */
    public Restaurant(int restID, String name, String address, String price, String cuisine, String phone, String website,double longitude, double latitude) {
        this.restID = restID;
        this.name = name;
        this.address = address;
        this.price = price;
        this.cuisine = cuisine;
        this.phone = phone;
        this.website = website;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * To know restaurant name
     */
    public String getName() {
        return name;
    }
    /**
     * To know restaurant Address
     */
    public String getAddress() {
        return address;
    }
    /**
     * To know restaurant Price
     */
    public String getPrice() {
        return price;
    }
    /**
     * To know restaurant Cuisine
     */
    public String getCuisine() {
        return cuisine;
    }
    /**
     * To know restaurant phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * To know restaurant Website
     */
    public String getWebsite() {
        return website;
    }
    /**
     * To know restaurant name
     */
    public int getRestID() {return restID;}

    /**
     * To know restaurant Comment
     */
    public ArrayList<Comment> getComments()
    {
        return comments;
    }
    /**
     * Add comment to a restaurant
     */
    public void addComment(Comment comm) {
        comments.add(comm);
    }
    /**
     * To know restaurant Longitude
     */
    public double getLongitude() {
        return longitude;
    }
    /**
     * To know restaurant tLatitude
     */
    public double getLatitude() {
        return latitude;
    }
    /**
     * Change to string
     */
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
