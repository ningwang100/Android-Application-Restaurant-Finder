/*
 * This fragment displays user's favorite restaurant list
 */
package com.example.jxq48.restaurant_finder.presentation.UserHost.myFavoriteList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.presentation.adapter.ListViewAdapter;
import com.example.jxq48.restaurant_finder.entities.Restaurant;
import com.example.jxq48.restaurant_finder.presentation.intents.IntentToRestaurantHost;
import com.example.jxq48.restaurant_finder.ws.remote.RemoteServerProxy;

import java.util.ArrayList;

public class displayMyfavorite extends Fragment /*implements customButtonListener*/ {
    private Context context;
    ListViewAdapter adapter;
    ArrayList<Restaurant> list;
    String username;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //Show my favorite list in a ListView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_display_myfavorite, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("UName", Context.MODE_PRIVATE);
         username = prefs.getString("username", "");
         RemoteServerProxy proxy = new RemoteServerProxy();
        list= proxy.getFavorites(username);
          ListView listView = (ListView)view.findViewById(R.id.listView);
         adapter = new ListViewAdapter(this.context, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = (Restaurant) adapter.getItem((int) adapter.getItemId(position));
                IntentToRestaurantHost intent1 = new IntentToRestaurantHost(getActivity());
                intent1.putExtra("RESTAURANT", restaurant);
                getActivity().startActivity(intent1);
            }
        });

        listView.setOnTouchListener(new OnTouchListener() {
            float x, y, upx, upy;
            //This method is used to detect user's motion
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = event.getX();
                    y = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    upx = event.getX();
                    upy = event.getY();
                    int position1 = ((ListView) view).pointToPosition((int) x, (int) y);
                    int position2 = ((ListView) view).pointToPosition((int) upx, (int) upy);

                    if (position1 == position2 && Math.abs(x - upx) > 10) {
                        View v = ((ListView) view).getChildAt(position1);
                        if (v != null)
                            removeListItem(v, position1);
                    }
                }
                return false;
            }

        });
        return view;
    }
    //This method delete an item which is selected by user
    protected void removeListItem(View rowView, final int position) {

        final Animation animation = (Animation) AnimationUtils.loadAnimation(rowView.getContext(), R.anim.item_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}

            public void onAnimationRepeat(Animation animation) {}

            public void onAnimationEnd(Animation animation) {
                if (list.size()>0) {

                    Restaurant restaurant = (Restaurant) adapter.getItem((int) adapter.getItemId(position));
                    RemoteServerProxy proxy = new RemoteServerProxy();
                    String result = proxy.deleteFavorite(username, restaurant.getRestID());
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    if (result.equals("success"))
                        Toast.makeText(getActivity(), "Delete from my favorite successfully",
                                Toast.LENGTH_SHORT).show();
                    animation.cancel();
                }
            }
        });


        rowView.startAnimation(animation);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = (FavoriteListActivity)activity;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


}
