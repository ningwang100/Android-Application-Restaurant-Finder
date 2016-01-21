/**
 * This class is used to start activity from User Host page to my
 * Mapsactivity
 */
package com.example.jxq48.restaurant_finder.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.jxq48.restaurant_finder.presentation.UserHost.findMyLocation.MapsActivity;

/**
 * Created by jxq48 on 7/18/15.
 */
public class IntentToMyLocation extends Intent {
    public IntentToMyLocation (Activity activity) {
        super(activity, MapsActivity.class);
    }
}
