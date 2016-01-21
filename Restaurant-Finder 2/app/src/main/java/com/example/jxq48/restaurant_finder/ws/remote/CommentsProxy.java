package com.example.jxq48.restaurant_finder.ws.remote;

import com.example.jxq48.restaurant_finder.entities.Comment;

import java.util.ArrayList;

/**
 * Created by jxq48 on 8/3/15.
 */
public interface CommentsProxy {
    public ArrayList<Comment> getComments(int restID);
    public String addComment(int restID, String text, String username);
}
