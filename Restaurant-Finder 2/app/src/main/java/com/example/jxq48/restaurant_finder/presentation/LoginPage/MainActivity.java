/**
 * This class is the start of UI, and has a fragment container.It may contains login
 * register and initializeDB fragment.It could implement user login, new user register
 * and administrator upload restaurant information functionalities.
 */
package com.example.jxq48.restaurant_finder.presentation.LoginPage;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.jxq48.restaurant_finder.R;

import java.lang.reflect.Method;


public class MainActivity extends Activity implements BackHandledInterface
{
    String phoneType = Build.MODEL;
    private BackHandledFragment mBackHandedFragment;
    TextView register;

    /**
     * The default fragment is login fragment.new user could click new user? register an
     * account.Administrator could click action bar to upload and manage restaurants.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (phoneType.equals("Nexus 7")) {
            setContentView(R.layout.activity_main2);

            register=(TextView)findViewById(R.id.Reg_user2);
            FragmentManager FM=getFragmentManager();
            FragmentTransaction FT=FM.beginTransaction();
            LoginFragment login = new LoginFragment();
            FT.add(R.id.login_container2,login);
            FT.commit();
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager FM = getFragmentManager();
                    FragmentTransaction FT = FM.beginTransaction();
                    RegisterFragment regFragment = new RegisterFragment();
                    FT.replace(R.id.login_container2, regFragment);
                    FT.commit();
                }
            });
        }
        else {
            setContentView(R.layout.activity_main);

            register = (TextView) findViewById(R.id.Reg_user);
            FragmentManager FM = getFragmentManager();
            FragmentTransaction FT = FM.beginTransaction();
            LoginFragment login = new LoginFragment();
            FT.add(R.id.login_container, login);
            FT.commit();
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager FM = getFragmentManager();
                    FragmentTransaction FT = FM.beginTransaction();
                    RegisterFragment regFragment = new RegisterFragment();
                    FT.replace(R.id.login_container, regFragment);
                    FT.commit();
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            administrator();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * This method is used to show icon in the action bar
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * This method us used to implement functionality about administrator post
     * restaurant information
     */
    public void administrator(){
        Fragment fr;
        fr = new InitializeDB();

        FragmentManager fm = getFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (phoneType.equals("Nexus 7"))
            fragmentTransaction.replace(R.id.login_container2, fr);

        else
            fragmentTransaction.replace(R.id.login_container, fr);

        fragmentTransaction.commit();
    }

    //Use to listen physical back button
    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }
    // Listen the physical back button is pressed
    @Override
    public void onBackPressed() {
        if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){
            if(getFragmentManager().getBackStackEntryCount() == 0){
                super.onBackPressed();
            }else{
                getFragmentManager().popBackStack();
            }
        }
    }
}
