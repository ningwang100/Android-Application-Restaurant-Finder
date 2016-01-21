package com.example.jxq48.restaurant_finder.ws.remote;

/**
 * Created by jxq48 on 8/3/15.
 */
public interface UsersProxy {
    public String insertUser(String username, String password, String name, String age);
    public String isValid(String username, String password);
}
