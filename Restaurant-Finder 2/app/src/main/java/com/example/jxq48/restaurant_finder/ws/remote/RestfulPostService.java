package com.example.jxq48.restaurant_finder.ws.remote;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jinxi on 7/18/15.
 */

//Use RESTful webservice to post some data to server.
public class RestfulPostService extends AsyncTask<String,Void,String>{
    @Override
    protected String doInBackground(String... params) {
        if (params.length < 0)
            return null;

        String output = "";
        String rst = "";
        try {
            URL targetUrl = new URL(params[0]);

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();

            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");


            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(params[0].getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpConnection.getInputStream())));


            System.out.println("Output from Server:\n");
            while ((output = responseBuffer.readLine()) != null) {
                System.out.println(output);
                rst = output;

            }

            httpConnection.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("register exception:"+e);
            e.printStackTrace();

        }
        return rst;

    }


}
