/**
 * This fragment is used to add an comment for a restaurant by a user
 */
package com.example.jxq48.restaurant_finder.presentation.RestaurantHost;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.entities.Restaurant;
import com.example.jxq48.restaurant_finder.ws.remote.RemoteServerProxy;

/**
 * Created by jxq48 on 7/18/15.
 */
public class AddCommFragment extends Fragment {

    EditText add_comment;
    int restID = 0;
    //User enter his comment and click submit button,this fragment could listen this button
    // and send the comment to server,then display it in this restaurant host page
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(
                R.layout.fragment_addcomment, container, false);
        add_comment = (EditText) v.findViewById(R.id.add_comment);

        Intent i = getActivity().getIntent();
        Restaurant restaurant = (Restaurant) i.getSerializableExtra("RESTAURANT");
        restID = restaurant.getRestID();
        Button submitButton = (Button) v.findViewById(R.id.addCommButton);
        submitButton.setOnClickListener(SubmitButtonClicked);
        return v;
    }
    //Listen submit button
    View.OnClickListener SubmitButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String Comm = add_comment.getText().toString();
            if (!Comm.equals("")) {
                String tmpComm = Comm.replaceAll("\\n", "DELETETO").replaceAll(" ", "TOBEDELETE");
                SharedPreferences prefs = getActivity().getSharedPreferences("UName", Context.MODE_PRIVATE);
                String username = prefs.getString("username", "");
                RemoteServerProxy proxy = new RemoteServerProxy();
                String result = proxy.addComment(restID, tmpComm, username);
                if (result.equals("success")) {
                    Toast.makeText(getActivity(), "Thanks for your comment",
                            Toast.LENGTH_SHORT).show();

                    add_comment.setText("");
                    CommentsFragment fr = new CommentsFragment();
                    FragmentManager fm = getFragmentManager();

                    FragmentTransaction fragmentTransaction = fm.beginTransaction();

                    fragmentTransaction.replace(R.id.fragment_add_comment, fr);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
            else {
                Toast.makeText(getActivity(), "Please input your comment!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

}
