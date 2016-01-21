/**
 * This class is used to start activity from User Host page to find
 * restaurant activity
 */
package com.example.jxq48.restaurant_finder.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.jxq48.restaurant_finder.presentation.UserHost.findRestaurant.FindRestaurantActivity;

/**
 * Created by jxq48 on 7/18/15.
 */
public class IntentToFindRestaurant extends Intent {
    public IntentToFindRestaurant (Activity activity) {
        super(activity, FindRestaurantActivity.class);
    }
}
