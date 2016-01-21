/**
 * This class is used to display restaurant information in a ViewList
 */
package com.example.jxq48.restaurant_finder.presentation.adapter;

/**
 * Created by wangning on 7/17/15.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.entities.Restaurant;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<Restaurant> list;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, ArrayList<Restaurant> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list.size()==0)
            return null;
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * To get valid information which will display in the ViewList
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Restaurant score = (Restaurant) this.getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.favorite_list_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.address = (TextView) convertView.findViewById(R.id.item_address);
            viewHolder.price = (TextView) convertView.findViewById(R.id.item_price);
            viewHolder.cuisine = (TextView) convertView.findViewById(R.id.item_cuisine);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(score.getName());
        viewHolder.price.setText(score.getPrice());
        viewHolder.address.setText(score.getAddress());
        viewHolder.cuisine.setText(score.getCuisine());
        return convertView;
    }

    public static class ViewHolder {
        TextView name, price,address,cuisine;
    }
}