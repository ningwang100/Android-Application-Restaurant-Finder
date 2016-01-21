/**
 * This class includes a restaurant all information such as name, address,price,cuisine,
 * web and images.It also provide four functionalists for user including camera, add comment,
 * add to favorite list and back to UserHost page
 */
package com.example.jxq48.restaurant_finder.presentation.RestaurantHost;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.entities.Restaurant;
import com.example.jxq48.restaurant_finder.presentation.intents.IntentToUserHost;
import com.example.jxq48.restaurant_finder.ws.remote.RemoteServerProxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jxq48 on 7/16/15.
 */
public class RestaurantHostActivity extends Activity {
    private TextView name;
    private TextView address;
    private TextView price;
    private TextView cuisine;
    private TextView phone;
    private TextView web;
    private Restaurant r;
    private ImageView Image;
    public static Context appContext;
    private Bitmap cameraBitmap;

    //To get restaurant information and there are tow kind of layouts
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent1 = this.getIntent();
        r = (Restaurant)intent1.getSerializableExtra("RESTAURANT");

        String phoneType = Build.MODEL;
        if (phoneType.equals("Nexus 7")) {
            setContentView(R.layout.activity_restaurant_host2);

            appContext = getApplicationContext();
            name = (TextView) findViewById(R.id.host_name2);
            name.setText(r.getName());
            address = (TextView) findViewById(R.id.host_address2);
            address.setText(r.getAddress());
            price = (TextView) findViewById(R.id.host_price2);
            price.setText(r.getPrice());
            cuisine = (TextView) findViewById(R.id.host_cuisine2);
            cuisine.setText(r.getCuisine());
            phone = (TextView) findViewById(R.id.host_phone2);
            phone.setText(r.getPhone());
            web = (TextView) findViewById(R.id.host_website2);
            web.setText(r.getWebsite());
            Image = (ImageView) findViewById(R.id.imageView2);
        }
        else {
            setContentView(R.layout.activity_restaurant_host);

            appContext = getApplicationContext();
            name = (TextView) findViewById(R.id.host_name);
            name.setText(r.getName());
            address = (TextView) findViewById(R.id.host_address);
            address.setText(r.getAddress());
            price = (TextView) findViewById(R.id.host_price);
            price.setText(r.getPrice());
            cuisine = (TextView) findViewById(R.id.host_cuisine);
            cuisine.setText(r.getCuisine());
            phone = (TextView) findViewById(R.id.host_phone);
            phone.setText(r.getPhone());
            web = (TextView) findViewById(R.id.host_website);
            web.setText(r.getWebsite());
            Image = (ImageView) findViewById(R.id.imageView);
        }
        //send image to server
        RemoteServerProxy proxy = new RemoteServerProxy();
        ArrayList<String> imagelist = proxy.getImageFromServer(String.valueOf(r.getRestID()));
        if (imagelist != null) {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File image = new File(directory, imagelist.get(0));
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            Image.setImageBitmap(bitmap);
        }
    }
    // add the restaurant into hia favorite list
    public void addfavorite(){
        SharedPreferences prefs = getSharedPreferences("UName",MODE_PRIVATE);
        String username = prefs.getString("username","");
        RemoteServerProxy proxy = new RemoteServerProxy();
        String result = proxy.addFavorite(username, r.getRestID());
        if (result.equals("success"))
            Toast.makeText(getApplicationContext(), "The restaurant is added to your favorite list!",
                    Toast.LENGTH_SHORT).show();
    }
    //Using to add action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurant, menu);
        return true;
    }
    //This method is used to display an icon in the action bar overview
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
    //This method is used to listening the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id ==R.id.action_Take_Picture ) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
            return true;
        }else if(id == R.id.action_addComment){
            addComment();
            return true;
        }else if(id == R.id.action_Add_Favorite){
            addfavorite();
            return true;
        }else if(id == R.id.action_Back){
            back();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // This method is used to back from Restaurant host page to user host page
    public void back(){
        IntentToUserHost intent = new IntentToUserHost(RestaurantHostActivity.this);
        startActivity(intent);
    }
    //This method is used to add comment for this restaurant
    public void addComment(){
        Fragment fr;

        fr = new AddCommFragment();

        FragmentManager fm = getFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_show_comments, fr);

        fragmentTransaction.commit();
    }
    //This method is used to save photos which take by user
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && data.getExtras() != null) {
            cameraBitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            cameraBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);


            FileOutputStream b = null;
            //name pictures as date.png
            String str = null;
            Date date = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            date = new Date();
            str = format.format(date);
            String fileName = str + ".png";

            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath = new File(directory, fileName);

            try {
                b = new FileOutputStream(mypath);
                cameraBitmap.compress(Bitmap.CompressFormat.PNG, 100, b);//write data in file
                String absolutePath = mypath.getAbsolutePath();

                RemoteServerProxy remoteServerProxy = new RemoteServerProxy();
                remoteServerProxy.uploadImage(absolutePath,String.valueOf(baos.size()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}