/**
 * This class is used to show photos by GridView.
 */
package com.example.jxq48.restaurant_finder.presentation.UserHost.Myphotos;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.presentation.adapter.GridViewAdapter;
import com.example.jxq48.restaurant_finder.ws.remote.RemoteServerProxy;
import java.io.File;
import java.util.ArrayList;

public class showPhotosActivity extends Activity {
    GridView gridView;
    ArrayList<Bitmap> imageIds = new ArrayList<Bitmap>();
    public static ArrayList<Bitmap> largePicIds= new ArrayList<>();
    String[] date;
    public static Context appContext1;
    //Using GridView display images and set listening on image click
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_photos);
        appContext1 = getApplicationContext();
        gridView = (GridView)findViewById(R.id.gridView);

        SharedPreferences prefs = this.getSharedPreferences("UName", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "");
        RemoteServerProxy proxy = new RemoteServerProxy();
        int number = Integer.valueOf(proxy.getPhotoNum(username));
        if (number > 0) {
            date = new String[number];
            for (int i = 0; i < number; i++) {
                ArrayList<String> imagelist = proxy.getPhotoFromServer(username, String.valueOf(i));
                ContextWrapper cw2 = new ContextWrapper(appContext1);
                File directory = cw2.getDir("photosDir", Context.MODE_PRIVATE);
                File image = new File(directory, imagelist.get(0));

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
                imageIds.add(bitmap);
                date[i] = imagelist.get(0);
            }
            largePicIds = imageIds;
            gridView.setAdapter(new GridViewAdapter(this, imageIds));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(appContext1, String.valueOf(date[position]), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
