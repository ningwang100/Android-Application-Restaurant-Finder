/**
 * Created by jxq48 on 7/18/15.
 */

package com.example.jxq48.restaurant_finder.ws.remote;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//Use RESTful webservice to get some data from server.
public class RestfulGetService extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        if (params.length < 0)
            return null;
        String output = "";
        String rst = "";

        try {
            URL restServiceURL = new URL(params[0]);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");

            Log.d("Jingwen", "" + httpConnection.getResponseCode());

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : "
                        + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpConnection.getInputStream())));


            System.out.println("Output from Server:  \n");

            while ((output = responseBuffer.readLine()) != null) {
                System.out.println(output);
                rst = output;
            }

            httpConnection.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return rst;

    }

}

