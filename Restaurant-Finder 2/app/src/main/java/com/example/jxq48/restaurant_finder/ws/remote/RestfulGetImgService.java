package com.example.jxq48.restaurant_finder.ws.remote;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;

import com.example.jxq48.restaurant_finder.presentation.RestaurantHost.RestaurantHostActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jxq48 on 7/28/15.
 */
//Use RESTful webservice to get an image file from server.
public class RestfulGetImgService extends AsyncTask<String,Void,Object>{

    @Override
    protected Object doInBackground(String... params) {

        ArrayList<String> results = new ArrayList<>();
        try {
            URL restServiceURL = new URL(params[1]);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");

            if (httpConnection.getResponseCode() != 200) {
                System.out.println("No images");
                return null;
            }
            String header = httpConnection.getHeaderField("imgFrom");
            results.add(header);
            InputStream is = httpConnection.getInputStream();

            OutputStream os = null;
            //store the image file locally
            ContextWrapper cw = new ContextWrapper(RestaurantHostActivity.appContext);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File mypath=new File(directory,header);

            os = new FileOutputStream(mypath);
            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            httpConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
