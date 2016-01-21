/**
 * This class is used to start activity from User Host page to
 * ShowphotosActivity
 */
package com.example.jxq48.restaurant_finder.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.jxq48.restaurant_finder.presentation.UserHost.Myphotos.showPhotosActivity;

/**
 * Created by jxq48 on 8/1/15.
 */
public class IntentToShowPhotos extends Intent {
    public IntentToShowPhotos (Activity activity) {
        super(activity, showPhotosActivity.class);
    }

}
