/**
 * This class is used to start activity from User Host page to
 * RestaurantHostActivity
 */
package com.example.jxq48.restaurant_finder.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.jxq48.restaurant_finder.presentation.RestaurantHost.RestaurantHostActivity;

/**
 * Created by jxq48 on 7/18/15.
 */
public class IntentToRestaurantHost extends Intent {
    public IntentToRestaurantHost (Activity activity) {
        super(activity, RestaurantHostActivity.class);
    }
}
