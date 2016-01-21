/**
 * This fragment is used to login this app for user.
 */
package com.example.jxq48.restaurant_finder.presentation.LoginPage;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.exception.ExceptionHandler;
import com.example.jxq48.restaurant_finder.presentation.intents.IntentToUserHost;
import com.example.jxq48.restaurant_finder.ws.remote.RemoteServerProxy;

/**
 * Created by jxq48 on 7/16/15.
 */
public class LoginFragment extends Fragment {
    EditText username;
    EditText password;
    String result = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment login_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     *This method used to get user's username and password,then send request to
     * server for verify whether these information is valid.If so, user could login
     * to UserHost page
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        username = (EditText) view.findViewById(R.id.etUsername);
        password = (EditText) view.findViewById(R.id.etPassword);

        Button login;
        login=(Button)view.findViewById(R.id.blogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    RemoteServerProxy proxy = new RemoteServerProxy();
                    try {
                        if (username.getText().toString().equals("") || password.getText().toString().equals(""))
                            Toast.makeText(getActivity(), "Username or password cannot be empty!",
                                    Toast.LENGTH_SHORT).show();
                        else {
                            result = proxy.isValid(username.getText().toString(), password.getText().toString());

                            if (result.equals("fail")) {
                                Toast.makeText(getActivity(), "Invalid username or password. Please input again!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences pref = getActivity().getSharedPreferences("UName", Context.MODE_APPEND);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("username", result);
                                editor.commit();

                                IntentToUserHost intent = new IntentToUserHost(getActivity());
                                getActivity().startActivity(intent);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    public String getUsername() {
        return result;
    }

}
