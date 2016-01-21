/**
 * This class mainly provide choices for user such as find restaurant,MyLocation,
 * MyFavoriteList and Myphotos.
 */
package com.example.jxq48.restaurant_finder.presentation.UserHost;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.presentation.intents.IntentToFavoriteList;
import com.example.jxq48.restaurant_finder.presentation.intents.IntentToFindRestaurant;
import com.example.jxq48.restaurant_finder.presentation.intents.IntentToMyLocation;
import com.example.jxq48.restaurant_finder.presentation.intents.IntentToShowPhotos;

/**
 * Created by jxq48 on 7/16/15.
 */
public class UserHostActivity extends TabActivity{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_host);
        // create the TabHost that will contain the Tabs
        TabHost tabHost = getTabHost();


        TabHost.TabSpec tab1 = tabHost.newTabSpec("Find Restaurant");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("My Location");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("My Favorite List");
        TabHost.TabSpec tab4 = tabHost.newTabSpec("Photo wall");
        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("Find Restaurant");
        tab1.setContent(new IntentToFindRestaurant(UserHostActivity.this));

        tab2.setIndicator("My Location");
        tab2.setContent(new IntentToMyLocation(UserHostActivity.this));

        tab3.setIndicator("My Favorite List");
        IntentToFavoriteList intent2 = new IntentToFavoriteList(UserHostActivity.this);
        tab3.setContent(intent2);

        tab4.setIndicator("Photo wall");
        tab4.setContent(new IntentToShowPhotos(UserHostActivity.this));

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.addTab(tab4);
    }

}
