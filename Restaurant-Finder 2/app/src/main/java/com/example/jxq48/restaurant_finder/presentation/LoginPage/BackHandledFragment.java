/**
 * This fragment is used to implement functionality of listening
 * physical back button build in tablet.
 */
package com.example.jxq48.restaurant_finder.presentation.LoginPage;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by jxq48 on 8/4/15.
 */
public abstract class BackHandledFragment extends Fragment{
    protected BackHandledInterface mBackHandledInterface;

    protected abstract boolean onBackPressed();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!(getActivity() instanceof BackHandledInterface)){
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        }else{
            this.mBackHandledInterface = (BackHandledInterface)getActivity();
        }
        mBackHandledInterface.setSelectedFragment(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
