/**
 * This class is used to start activity from mainActivity
 * UserHostActivity
 */
package com.example.jxq48.restaurant_finder.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.jxq48.restaurant_finder.presentation.UserHost.UserHostActivity;

/**
 * Created by jxq48 on 7/18/15.
 */
public class IntentToUserHost extends Intent {
    public IntentToUserHost (Activity activity) {
        super(activity, UserHostActivity.class);
    }
}
