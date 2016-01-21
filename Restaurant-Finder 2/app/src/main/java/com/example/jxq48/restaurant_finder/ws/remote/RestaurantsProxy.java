package com.example.jxq48.restaurant_finder.ws.remote;

import com.example.jxq48.restaurant_finder.entities.Restaurant;

import java.util.ArrayList;

/**
 * Created by jxq48 on 8/3/15.
 */
public interface RestaurantsProxy {
    public ArrayList<Restaurant> getFavorites(String username);
    public String deleteFavorite(String username, int restID);
    public String addFavorite(String username, int restID);
    public ArrayList<Restaurant> getRestaurantsByName(String restName);
    public ArrayList<Restaurant> getRestaurantsByAddress(String restAddress);
    public ArrayList<Restaurant> getRestaurantsByCuisine(String restCuisine);
    public ArrayList<Restaurant> getNearbyRestaurant(double longitude, double latitude );
}
