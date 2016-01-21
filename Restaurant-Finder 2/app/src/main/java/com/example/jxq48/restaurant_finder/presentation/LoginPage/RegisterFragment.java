/**
 * This fragment is used to register an account for new user
 */
package com.example.jxq48.restaurant_finder.presentation.LoginPage;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.exception.ExceptionHandler;
import com.example.jxq48.restaurant_finder.ws.remote.RemoteServerProxy;

/**
 * Created by jxq48 on 7/16/15.
 */
public class RegisterFragment extends BackHandledFragment {

    EditText name;
    EditText age;
    EditText username;
    EditText password;
    private boolean hadIntercept;


    private LoginFragment.OnFragmentInteractionListener mListener;
    public RegisterFragment() {
    }

    /**
     * This method is used to register a new account and also handle exceptions which may
     * happen in registeration process
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register, container, false);
        Button register=(Button)view.findViewById(R.id.bRegister);
        name = (EditText) view.findViewById(R.id.etName);
        age = (EditText) view.findViewById(R.id.etAge);
        username = (EditText) view.findViewById(R.id.etRegUsername);
        password = (EditText) view.findViewById(R.id.etRegPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    RemoteServerProxy proxy = new RemoteServerProxy();
                    try {
                        if (username.getText().toString().equals("") || password.getText().toString().equals(""))
                            Toast.makeText(getActivity(), "Username or password cannot be empty!",
                                    Toast.LENGTH_SHORT).show();
                        else {
                            String tmpage = age.getText().toString();
                            String tmpname = name.getText().toString();
                            String phoneType = Build.MODEL;

                            if (tmpage.equals(""))
                                tmpage = String.valueOf(0);
                            if (tmpname.equals(""))
                                tmpname = "Anonymity";
                            String insert = proxy.insertUser(username.getText().toString(), password.getText().toString(), tmpname, tmpage);
                            if (insert.equals("success")) {
                                Toast.makeText(getActivity(), "Thanks for your registration!",
                                        Toast.LENGTH_SHORT).show();
                                FragmentManager FM = getFragmentManager();
                                FragmentTransaction FT = FM.beginTransaction();
                                LoginFragment login = new LoginFragment();
                                if (phoneType.equals("Nexus 7"))
                                    FT.replace(R.id.login_container2, login);
                                else
                                    FT.replace(R.id.login_container, login);
                                FT.commit();
                            } else {
                                Toast.makeText(getActivity(), "Username has been registered. Please input again!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        ExceptionHandler handler = new ExceptionHandler();
                        handler.fix(getActivity(), "Null input!");
                    }

            }
        });
        return view;
    }

    @Override
    protected boolean onBackPressed() {
        if(hadIntercept){
            return false;
        }else{
            Intent intent = new Intent(getActivity(),
                    MainActivity.class);
            startActivity(intent);
            hadIntercept = true;
            return true;
        }
    }
}
