/**
 * This class is used to post restaurant and user information in this app by
 * Administrator
 */
package com.example.jxq48.restaurant_finder.presentation.LoginPage;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
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
 * Created by jxq48 on 8/4/15.
 */
public class InitializeDB extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    String result = "";
    EditText Administrator;
    EditText password;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InitializeDb.
     */
    // TODO: Rename and change types and number of parameters
    public static InitializeDB newInstance(String param1, String param2) {
        InitializeDB fragment = new InitializeDB();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public InitializeDB() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_initialize, container, false);
        Button initialize=(Button)view.findViewById(R.id.verify);
        Administrator = (EditText) view.findViewById(R.id.etAdministrator);
        password = (EditText) view.findViewById(R.id.etverifyPassword);
        initialize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteServerProxy proxy = new RemoteServerProxy();
                try {
                    if(Administrator.getText().toString().equals("peter")&&password.getText().toString().equals("peter")){
                        proxy.initialDB();
                        FragmentManager FM = getFragmentManager();
                        FragmentTransaction FT = FM.beginTransaction();
                        LoginFragment login = new LoginFragment();
                        String phoneType = Build.MODEL;
                        if (phoneType.equals("Nexus 7"))
                        FT.replace(R.id.login_container2, login);
                        else
                            FT.replace(R.id.login_container, login);
                        FT.commit();
                    }else{
                        Toast.makeText(getActivity(), "Invalid Administrator or password. Please input again!",
                                Toast.LENGTH_LONG).show();
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

}
