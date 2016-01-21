/**
 *In this class,it create the comment model which is used to pass information from UI and server.
 */
package com.example.jxq48.restaurant_finder.entities;


public class Comment {
    
    private String text;
    private String username;
    //To know the comment content
    public String getText() {
        return text;
    }
    //To know the username who add the corresponding comment
    public String getUsername() {
        return username;
    }
    //Initialize the model
    public Comment(String text, String username) {
        this.text = text;
        this.username = username;
    }


}
