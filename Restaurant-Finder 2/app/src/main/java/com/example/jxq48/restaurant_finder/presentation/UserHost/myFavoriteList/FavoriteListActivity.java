/**
 * This class is used to show user favorite restaurant list
 */
package com.example.jxq48.restaurant_finder.presentation.UserHost.myFavoriteList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.jxq48.restaurant_finder.R;

/**
 * Created by jxq48 on 7/16/15.
 */
public class FavoriteListActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Bundle bundle = new Bundle();
        displayMyfavorite displayFrag = new displayMyfavorite();
        displayFrag.setArguments(bundle);
        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.add(R.id.displayMyfavorite_container, displayFrag);
        FT.commit();
    }
}
