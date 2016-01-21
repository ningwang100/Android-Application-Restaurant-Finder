/**
 * This fragment is used to display the comment list about a restaurant
 */
package com.example.jxq48.restaurant_finder.presentation.RestaurantHost;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.entities.Comment;
import com.example.jxq48.restaurant_finder.entities.Restaurant;
import com.example.jxq48.restaurant_finder.presentation.adapter.CommentsViewAdapter;
import com.example.jxq48.restaurant_finder.ws.remote.RemoteServerProxy;

import java.util.ArrayList;

/**
 * Created by jxq48 on 7/17/15.
 */
public class CommentsFragment extends Fragment {

    private Restaurant restaurant;
    // send request to server and get comment list about a restaurant
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(
                R.layout.fragment_comments, container, false);

        Intent i = getActivity().getIntent();
        restaurant = (Restaurant) i.getSerializableExtra("RESTAURANT");
        int restID = restaurant.getRestID();
        RemoteServerProxy proxy = new RemoteServerProxy();
        if (proxy.getComments(restID) != null) {

            ArrayList<Comment> comms = proxy.getComments(restID);
            ListView lv = (ListView) v.findViewById(R.id.comments_list);
            lv.setAdapter(new CommentsViewAdapter(getActivity(), comms));

        }
        return v;
    }
}
