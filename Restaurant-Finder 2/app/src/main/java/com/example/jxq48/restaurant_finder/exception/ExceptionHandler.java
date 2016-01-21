/**
 * In this class,it maily handle some exceptions occured in the UI. We fix these exception
 * in our program and toast a error information using the fix method.
 *
 */
package com.example.jxq48.restaurant_finder.exception;
import android.app.Activity;
import android.widget.Toast;

/**
 * Created by jxq48 on 7/30/15.
 */
public class ExceptionHandler {
    //Fix these exceptions and toast the error information
    public void fix(Activity activity, String error){
        Toast.makeText(activity, error,
                Toast.LENGTH_SHORT).show();
    }
}
