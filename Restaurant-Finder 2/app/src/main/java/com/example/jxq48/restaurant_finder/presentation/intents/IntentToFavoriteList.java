/**
 * This class is used to start activity from User Host page to my
 * Favorite list activity
 */
package com.example.jxq48.restaurant_finder.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.jxq48.restaurant_finder.presentation.UserHost.myFavoriteList.FavoriteListActivity;

/**
 * Created by jxq48 on 7/18/15.
 */
public class IntentToFavoriteList extends Intent {
    public IntentToFavoriteList (Activity activity) {
        super(activity, FavoriteListActivity.class);
    }
}
