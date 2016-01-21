package com.example.jxq48.restaurant_finder.ws.remote;

import android.os.AsyncTask;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by jxq48 on 7/31/15.
 */

//Use Socket webservice to upload an image file to server.
public class RestfulPostImgService extends AsyncTask<String,Void,String> {
    private int RESERVED = 1000;

    @Override
    protected String doInBackground(String... params) {
        uploadImageThroughSocket(params[0], params[1]);

        return "upload!";
    }

    private void uploadImageThroughSocket(String fileStreamPath, String fileLength) {

        Socket client;
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream;
        OutputStream outputStream;
        try {

            client = new Socket(RemoteServerProxy.TESTIP, 15214);

            byte[] mybytearray = new byte[Integer.valueOf(fileLength) + RESERVED]; //create a byte array to file

            fileInputStream = new FileInputStream(fileStreamPath);
            bufferedInputStream = new BufferedInputStream(fileInputStream);

            bufferedInputStream.read(mybytearray, 0, mybytearray.length); //read the file

            outputStream = client.getOutputStream();

            outputStream.write(mybytearray, 0, mybytearray.length); //write file to the output stream byte by byte
            outputStream.flush();
            bufferedInputStream.close();
            outputStream.close();
            client.close();




        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
