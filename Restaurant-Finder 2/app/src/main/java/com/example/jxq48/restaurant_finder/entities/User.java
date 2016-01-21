/**
 * In this class,it create the user model which used to save user information and interact
 * with server.
 */
package com.example.jxq48.restaurant_finder.entities;

import java.util.ArrayList;

/**
 * Created by jxq48 on 7/17/15.
 */
public class User {
    private String username;
    private String password;
    private String name;
    private String age;
    private ArrayList<Restaurant> favoriteList = new ArrayList<Restaurant>();
    /**
     * Using constructor with arguments to initialize these variables and User object
     */
    public User(String username, String password, String name, String age) {
     this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
    }
    /**
     * To update user favorite list
     */
    public void updateFavoriteList(Restaurant restaurant) {
        favoriteList.add(restaurant);
    }
    /**
     * To know username
     */
    public String getUsername() {
        return username;
    }
    /**
     * To know user password
     */
    public String getPassword() {
        return password;
    }
    /**
     * To know user name
     */
    public String getName() {
        return name;
    }
    /**
     * To know user age
     */
    public String getAge() {
        return age;
    }
    /**
     * To get user favorite restaurant list
     */
    public ArrayList<Restaurant> getFavoriteList() {
        return favoriteList;
    }
}
