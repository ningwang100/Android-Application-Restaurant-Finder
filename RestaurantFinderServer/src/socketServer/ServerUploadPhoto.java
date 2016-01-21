package socketServer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.prgguru.jersey.MyServer;
/*The server using socket to listening to the port 15214 to give a photo from stream*/
public class ServerUploadPhoto {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static InputStream inputStream;
    private static FileOutputStream fileOutputStream;
    private static BufferedOutputStream bufferedOutputStream;
    private static int filesize = 10000000; // filesize temporary hardcoded 
    private static int bytesRead;
    private static int current = 0;
    public static void main(String[] args) throws IOException {


        serverSocket = new ServerSocket(15214);  //Server socket

        System.out.println("Server started. Listening to the port 15214");

        //Make sure Upload photo server is keep running
        while (true) {
        clientSocket = serverSocket.accept();


        byte[] mybytearray = new byte[filesize];    //create byte array to buffer the file

        inputStream = clientSocket.getInputStream();
        String filename = String.valueOf(System.currentTimeMillis());
        fileOutputStream = new FileOutputStream(MyServer.DIRECTIORY+"photos/"+filename+".jpg");
        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        System.out.println("Receiving...");

        //following lines read the input slide file byte by byte
        bytesRead = inputStream.read(mybytearray, 0, mybytearray.length);
        current = bytesRead;

        do {
            bytesRead = inputStream.read(mybytearray, current, (mybytearray.length - current));
            if (bytesRead >= 0) {
                current += bytesRead;
            }
        } while (bytesRead > -1);


        bufferedOutputStream.write(mybytearray, 0, current);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        inputStream.close();
        clientSocket.close();
        System.out.println("Sever recieved the file");

        }
     
    }
}
