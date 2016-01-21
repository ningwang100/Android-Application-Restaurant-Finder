/***
 * This class is used to implement functionality about find restaurants
 * There are three ways find restaurant
 */
package com.example.jxq48.restaurant_finder.presentation.UserHost.findRestaurant;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.exception.ExceptionHandler;
import com.example.jxq48.restaurant_finder.presentation.adapter.ListViewAdapter;

import com.example.jxq48.restaurant_finder.entities.Restaurant;
import com.example.jxq48.restaurant_finder.presentation.adapter.ShakeListener;
import com.example.jxq48.restaurant_finder.presentation.intents.IntentToRestaurantHost;
import com.example.jxq48.restaurant_finder.ws.remote.RemoteServerProxy;

import java.util.ArrayList;

/**
 * Created by jxq48 on 7/16/15.
 */
public class FindRestaurantActivity extends Activity /*implements customButtonListener*/ {
    ListViewAdapter adapter1;
    ArrayList<Restaurant> list = null;
    EditText searchEditText;
    private ShakeListener mShakeListener = null;
    private Vibrator mVibrator;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_restaurant);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
    }
    //Choose one of methods to find restaurant.Methods include find restaurant by address,
    //name or cuisine
    public void selectFrag(View view) {
        try {
            String searchText = searchEditText.getText().toString().replaceAll(" ", "");

            if (view == findViewById(R.id.NameButton)) {
                    RemoteServerProxy proxy = new RemoteServerProxy();
                    list = proxy.getRestaurantsByName(searchText);
            }

            if (view == findViewById(R.id.AddressButton)) {
                if (!searchText.equals("")) {
                    RemoteServerProxy proxy = new RemoteServerProxy();
                    list = proxy.getRestaurantsByAddress(searchText);
                }

            }

            if (view == findViewById(R.id.CuisineButton)) {
                RemoteServerProxy proxy = new RemoteServerProxy();
                list = proxy.getRestaurantsByCuisine(searchText);
            }
            if (list.size()==0)
                Toast.makeText(this, "No result!",
                        Toast.LENGTH_SHORT).show();

            final ListView lv = (ListView) findViewById(R.id.restaurants_list);
            adapter1 = new ListViewAdapter(this, list);
            lv.setAdapter(adapter1);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Restaurant restaurant = (Restaurant) adapter1.getItem((int) adapter1.getItemId(position));
                    IntentToRestaurantHost intent1 = new IntentToRestaurantHost(FindRestaurantActivity.this);
                    intent1.putExtra("RESTAURANT", restaurant);
                    startActivityForResult(intent1, 0);
                }
            });
            // choose a restaurant randomly by shaking tablet
            mVibrator = (Vibrator) getApplication().getSystemService(
                    VIBRATOR_SERVICE);
            mShakeListener = new ShakeListener(FindRestaurantActivity.this);
            mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
                public void onShake() {
                    mShakeListener.stop();
                    startVibrato();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int max = list.size();
                            if (max > 0) {
                                int temp = (int) (Math.random() * max);

                                Restaurant rest = list.get(temp);
                                Toast.makeText(getApplicationContext(), "Recommend a restaurant for you!",
                                        Toast.LENGTH_SHORT).show();
                                IntentToRestaurantHost intent2 = new IntentToRestaurantHost(FindRestaurantActivity.this);
                                intent2.putExtra("RESTAURANT", rest);
                                startActivity(intent2);
                            }
                            mVibrator.cancel();
                            mShakeListener.start();
                        }
                    }, 2000);
                }
            });
        }catch (Exception e) {
            ExceptionHandler handler = new ExceptionHandler();
            handler.fix(this, "Null input!");
        }
    }
    public void startVibrato() {
        mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1);
    }
}
