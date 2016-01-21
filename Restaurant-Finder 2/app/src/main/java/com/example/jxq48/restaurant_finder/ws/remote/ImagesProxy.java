package com.example.jxq48.restaurant_finder.ws.remote;

import java.util.ArrayList;

/**
 * Created by jxq48 on 8/3/15.
 */
public interface ImagesProxy {
    public ArrayList<String> getImageFromServer(String restID);
    public void uploadImage(String filePath, String fileLength);
    public String getPhotoNum(String username);
    public ArrayList<String> getPhotoFromServer(String username, String number);
}
